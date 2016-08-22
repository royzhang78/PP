package com.rodcell.service.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.AppleItunesUtil;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MD5Util;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.PayProduct;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2015年2月13日 上午10:56:57 
 * 类说明 
 */
@Service(value="applePayService")
public class ApplePaySuccessServiceImpl implements ApiService {

	private static Logger logger = Logger.getLogger(ApplePaySuccessServiceImpl.class);
	
	@Autowired
	private ApiService orderService;
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	@Autowired
	private PayMainRetryDao payMainRetryDao;
	
//	@Autowired
//	private PayProductDao payProductDao;
	
	
	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public synchronized ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		String orderid = MapsUtil.getString(map, new String[]{"orderid"});//订单id
		String verify = MapsUtil.getString(map, new String[]{"verify"});//验证key
		ReturnObj returnobj=new ReturnObj(ErrorCode.ERROR_500);
		
		PayMainDao dao =(PayMainDao)Constant.CTX.getBean("payMainDao");
		
		PayServerDao payServerDao =(PayServerDao)Constant.CTX.getBean("payServerDao");
		PayProductDao payProductDao =(PayProductDao)Constant.CTX.getBean("payProductDao");		
		PayMain main = dao.findpayMainById(orderid);
		
		
		if(main==null){
			returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);			
			returnobj.setReturnMsg(ErrorCode.ORDER_ISNULL_ERROR_MSG);
			return returnobj;
		}
		
		String posturl="";
		String  apple_check_url_test=sysPayStaticConfigDao.findPayStaticConfigbyKey("apple_check_url"); 
		String  apple_check_url_test_Sandbox=sysPayStaticConfigDao.findPayStaticConfigbyKey("apple_check_url_test"); 
		posturl=apple_check_url_test;
		if(main.getIsSandbox()==Constant.ISSANDBOX_1){
			posturl=apple_check_url_test_Sandbox;
		}
		String md5verify= MD5Util.getMD5String(verify);
		
		PayMain main1 = dao.findpayMainByByunique_keyAndType(md5verify, "12");//查询苹果票据是否使用过
		if(main1!=null){
			returnobj.setReturnCode(ErrorCode.ORDER_STATUS_ERROR);		
			return returnobj;
		}
		
		AppleItunesUtil apppost=new AppleItunesUtil();
		Map p = new HashMap();
		
//		PayProduct pp = payProductDao.findPayProductById(main.getS_id()+"");
		p.put("receipt-data", verify);
		String par=JSONUtil.objectToString(p);
		try {
			if(main.getPay_status()<Constant.PAY_STATUS_4){
				
				if(main.getPay_status()==Constant.PAY_STATUS_4 || main.getPay_status()==Constant.PAY_STATUS_5){
					returnobj.setReturnCode(ErrorCode.NO_ERROR);		
					return returnobj;
				}
				
				
				String rs = apppost.Connection(posturl, null, par);
				
				if(rs==null){
					returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);		//苹果未响应	
					main.setPay_status(Constant.PAY_STATUS_4);
					dao.updatePayMain(main);
					return returnobj;
				}
				
				if(!rs.contains("quantity")){
					logger.error("paymian"+JSONUtil.objectToString(main)+"rs:"+rs);
					returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);//苹果未响应	
					return returnobj;
				}
				
//				JSONObject o=JSONObject.fromObject(rs);
				Map o = JSONUtil.JsonToMap(rs);
				int status=Integer.parseInt(String.valueOf(o.get("status")));
//				main.setStep1(par);
				main.setStep2(rs);
				main.setUnique_key(md5verify);
				if(status!=0){
					main.setPay_status(Constant.PAY_STATUS_6);
					dao.updatePayMain(main);
					returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);		//苹果验证未通过
					return returnobj;
				}
				
				main.setPay_status(Constant.PAY_STATUS_4);
				
				
				Date date = new Date();
				PayMainRetry retry=new PayMainRetry(main.getPay_id(), Constant.serverkey+"", date, 
							0,date, 0, null, null, null, 0);
				boolean b = payMainRetryDao.inserPayMainRetry(retry);
				dao.updatePayMain(main);
				logger.info("insert into retry"+b);
				returnobj.setReturnObjs(main);
				returnobj.setReturnCode(ErrorCode.NO_ERROR);		
				return returnobj;
			}else{
				returnobj.setReturnCode(ErrorCode.PAY_MAIN_STATUS_ERROR1004);
				return returnobj;
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		returnobj.setReturnCode(ErrorCode.ERROR_500);
		return returnobj;
	}

	

}
