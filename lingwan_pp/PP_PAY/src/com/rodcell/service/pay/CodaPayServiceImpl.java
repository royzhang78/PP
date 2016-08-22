package com.rodcell.service.pay;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MD5Util;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.service.CodaPayService;
import com.rodcell.service.UniPayService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * 适用客户端充值完毕后通知pp 支付系统
 * @version 创建时间：2014年6月6日 下午5:00:15 
 * 类说明 
 */
@Service(value="codaPayService")
public class CodaPayServiceImpl  implements CodaPayService{

	private static Logger logger = Logger.getLogger(CodaPayServiceImpl.class);
	
	
	@Autowired
	private ApiService orderService;
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	@Autowired
	private PayServerChannelDao payServerChannelDao;
	

	@Autowired
	private PayServerDao payServerDao;
	

	@Autowired
	private PayMainDao payMainDao;
	

	@Autowired
	private PayMainRetryDao payMainRetryDao;
	
 
	



	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public ReturnObj Completion(Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		ReturnObj returnobj=new ReturnObj(ErrorCode.NO_ERROR); 
		String TxnId=MapsUtil.getString(par, "TxnId");
		String OrderId=MapsUtil.getString(par, "OrderId");
		
		
		PayMainDao dao =(PayMainDao)Constant.CTX.getBean("payMainDao");
		
		PayServerDao payServerDao =(PayServerDao)Constant.CTX.getBean("payServerDao");
		PayProductDao payProductDao =(PayProductDao)Constant.CTX.getBean("payProductDao");		
		PayMain main = dao.findpayMainById(OrderId);
		if(main==null){
			returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);			
			returnobj.setReturnMsg(ErrorCode.ORDER_ISNULL_ERROR_MSG); 
			return returnobj;
		}
		main.setPay_status(Constant.PAY_STATUS_1);
		main.setUnique_key(TxnId);
		dao.updatePayMain(main);
		
		return returnobj;
	}




	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public ReturnObj notification(Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		ReturnObj returnobj=new ReturnObj(ErrorCode.NO_ERROR); 
		String TxnId=MapsUtil.getString(par, "TxnId");
		String OrderId=MapsUtil.getString(par, "OrderId");
		String ResultCode=MapsUtil.getString(par, "ResultCode");
		
		PayMainDao dao =(PayMainDao)Constant.CTX.getBean("payMainDao");
		
		PayServerDao payServerDao =(PayServerDao)Constant.CTX.getBean("payServerDao");
		PayProductDao payProductDao =(PayProductDao)Constant.CTX.getBean("payProductDao");		
		PayMain main = dao.findpayMainById(OrderId);
		if(main!=null){
			if(main.getPay_status()<=Constant.PAY_STATUS_4){
				String status = "";
				
				if(ResultCode.equals("0")){
					main.setPay_status(Constant.PAY_STATUS_4);
					main.setUnique_key(TxnId);
					main.setStep3(JSONUtil.objectToString(par));
					dao.updatePayMain(main);
					
					Date date = new Date();
					PayMainRetry retry=new PayMainRetry(main.getPay_id(), Constant.serverkey+"", date, 
								0,date, 0, null, null, null, 0);
					payMainRetryDao.inserPayMainRetry(retry);
				}else{
					main.setPay_status(Constant.PAY_STATUS_6);
					main.setStep3(JSONUtil.objectToString(par));
					main.setUnique_key(TxnId);
					main.setFail_code(ResultCode);
					dao.updatePayMain(main);
				}
			}else{
				returnobj.setReturnCode(ErrorCode.PAY_MAIN_STATUS_ERROR1004);			
				returnobj.setReturnMsg(ErrorCode.ORDER_ISNULL_ERROR_MSG); 
				return returnobj;
			}
		}else{
			returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);			
			returnobj.setReturnMsg(ErrorCode.ORDER_ISNULL_ERROR_MSG); 
			return returnobj;
		}
		
		
		return returnobj;
	}



 
	
	

}
