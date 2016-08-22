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
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2015年2月13日 上午10:56:57 
 * 类说明 广告类支付确认
 */
@Service(value="AdColonyService")
public class AdColonyServiceImpl implements ApiService {

	private static Logger logger = Logger.getLogger(AdColonyServiceImpl.class);
	
	@Autowired
	private ApiService orderService;
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	@Autowired
	private PayMainRetryDao payMainRetryDao;
	
	
	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public synchronized ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		String ppid = MapsUtil.getString(map, new String[]{"custom_id"});//订单id
		String id = MapsUtil.getString(map, new String[]{"id"});
		String amount = MapsUtil.getString(map, new String[]{"amount"});
		String currency = MapsUtil.getString(map, new String[]{"currency"});
		
		
		
		ReturnObj returnobj=new ReturnObj(ErrorCode.ERROR_500);
		
		PayMainDao dao =(PayMainDao)Constant.CTX.getBean("payMainDao");
		
		PayServerDao payServerDao =(PayServerDao)Constant.CTX.getBean("payServerDao");
		PayProductDao payProductDao =(PayProductDao)Constant.CTX.getBean("payProductDao");		
		PayMain main = dao.selectPayMainByADColonyOrder(ppid);
		
		
		if(main==null){
			returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);			
			returnobj.setReturnMsg(ErrorCode.ORDER_ISNULL_ERROR_MSG);
			return returnobj;
		}
		
//		String posturl="";
//		String  apple_check_url_test=sysPayStaticConfigDao.findPayStaticConfigbyKey("apple_check_url"); 
//		String  apple_check_url_test_Sandbox=sysPayStaticConfigDao.findPayStaticConfigbyKey("apple_check_url_test"); 
//		posturl=apple_check_url_test;
//		if(main.getIsSandbox()==Constant.ISSANDBOX_1){
//			apple_check_url_test=apple_check_url_test_Sandbox;
//		}
		
//		AppleItunesUtil apppost=new AppleItunesUtil();
		Map p = new HashMap();
		
//		p.put("receipt-data", verify);
		String par=JSONUtil.objectToString(p);
		try {
			if(main.getPay_status()<Constant.PAY_STATUS_4){
//				String rs = apppost.Connection(posturl, null, par);
				
				
//				if(rs==null){
//					returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);		//苹果未响应	
//					main.setPay_status(Constant.PAY_STATUS_4);
//					dao.updatePayMain(main);
//					return returnobj;
//				}
				
//				JSONObject o=JSONObject.fromObject(rs);
//				int status=Integer.parseInt(String.valueOf(o.get("status")));
//				if(status!=0){
//					main.setPay_status(Constant.PAY_STATUS_4);
//					dao.updatePayMain(main);
//					returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);		//苹果验证未通过
//					return returnobj;
//				}
				main.setUnique_key(id);
				main.setAmount(amount);
				main.setPay_status(Constant.PAY_STATUS_4);
				main.setStep2(JSONUtil.objectToString(map));
				main.setServer_order_info(amount);
				main.setCurrency(currency);
			
				
				Date date = new Date();
				PayMainRetry retry=new PayMainRetry(main.getPay_id(), Constant.serverkey+"", date, 
							0,date, 0, null, null, null, 0);
				boolean b = payMainRetryDao.inserPayMainRetry(retry);
				dao.updatePayMain(main);
				logger.info("insert into retry"+b);
				
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
