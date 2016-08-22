package com.rodcell.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.ChannelParser;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainErrorDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainError;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.comm.ChannelAnalyze;
import com.rodcell.entity.comm.ChannelEntity;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.PayMessageService;
import com.rodcell.service.analyze.AnalyzeService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月20日 上午11:16:27 
 * 类说明 
 */
@Service(value="terminatedService")
public class TerminatedServiceImpl implements PayMessageService{
 
	
	@Autowired
	private PayMainDao payMainDao;
	
//	@Autowired
//	private MQMessage mqTemplate;
	
	@Autowired
	private PayMainRetryDao payMainRetryDao;
	
	@Autowired
	private PayMainErrorDao payMainErrorDao;
	
	/**
	 *  消息结束接口流程 该接口必须解析出参数中的
	 *  int isstatus//必须解析字段  0代表使用系统支付id（pay_id）,1代表使用 支付渠道方id（unique_key）
	 *  String pay_id;//支付id
	 *  int pay_status;//根据渠道方验证状态返回 系统 支付状态，
	 *  String unique_key;//支付方渠道id
	 *  String fail_code;//渠道方验证状态
	 *  String error_desc;//渠道方错误内容
	 * @throws SException 
	 */
	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public Map service(long channelid, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SException {
		boolean exestatus = false;
		int responseStatus=ErrorCode.RESPONSE200;
		int responseCode=Integer.parseInt(ErrorCode.NO_ERROR);
		ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(channelid));
		
	
		
		
		List<ChannelAnalyze> list = c.getNotif().getAnalyze();
		
		boolean b1 = KeyCheckImpl.service(c.getNotif(), par, request, response, session);
		if(!b1){//如果有消息加密则验证
			responseStatus=ErrorCode.RESPONSE500;
			responseCode=Integer.parseInt(ErrorCode.PAY_KEY_ERROR);		 
			par.put(Constant.responseStatus, responseStatus);
			par.put(Constant.responseCode, responseCode);
			return par;
		}
		
		
		String pay_id=null;
		String unique_key=null;
		String successStatus=null;
		for (ChannelAnalyze ca:list) {
			if("payId".equals(ca.getParName())){
				pay_id=MapUtils.getString(par, ca.getSource());
				if(ca.getExeClass()!=null && ca.getExeClass().length()>0){
					AnalyzeService service = (AnalyzeService)Constant.CTX.getBean(ca.getExeClass().trim());
					pay_id = service.service(pay_id,request);
				}
			}
			if("unique_key".equals(ca.getParName())){
				unique_key=MapUtils.getString(par, ca.getSource());
				if(ca.getExeClass()!=null && ca.getExeClass().length()>0){
					AnalyzeService service = (AnalyzeService)Constant.CTX.getBean(ca.getExeClass().trim());
					unique_key = service.service(unique_key,request);
				}
			}
			
		}
		successStatus = MapUtils.getString(par, c.getNotif().getSuccessStatus().getParName());
		PayMain payMain = null;
		if(!StringUtil.isNullOrEmpty(pay_id)){
			payMain=payMainDao.findpayMainById(pay_id);
		}else if(!StringUtil.isNullOrEmpty(unique_key)){
			payMain=payMainDao.findpayMainByByunique_keyAndType(unique_key,channelid+"");
		}
		if(payMain!=null && payMain.getPay_status()<=Constant.PAY_STATUS_1 && payMain.getPay_channel_type()==channelid){//订单存在并且状态为创建状态
			payMain.setStep2(JSONUtil.objectToString(par));
			boolean b =false;
			if(successStatus.equals(c.getNotif().getSuccessStatus().getCode())){//充值正常完成
				payMain.setPay_status(Constant.PAY_STATUS_4);
				b=true;
			}else{//
				payMain.setPay_status(Constant.PAY_STATUS_6);
				payMain.setFail_code(MapUtils.getString(par, c.getNotif().getErrorText()));
				//payMain.setError_desc(MapUtils.getString(par, c.getNotif().getErrorText()));
			}
			 
			payMain.setSucccess_pay_date(new Date());
			String sname = c.getNotif().getExeServerByServiceName();
			if(!StringUtil.isNullOrEmpty(sname)){
				PayMessageService service = (PayMessageService)Constant.CTX.getBean(sname);
				Map exe = service.service(channelid, par, request, response, session);
				par.put("exePar", exe);
			}
			
			if(b){//插入充值表，根据充值类型进行操作
				Date date = new Date();
				PayMainRetry retry=new PayMainRetry(payMain.getPay_id(), Constant.serverkey+"", date, 
							0,date, 0, null, null, null, 0);
				payMainRetryDao.inserPayMainRetry(retry);
			}
			
			payMainDao.updatePayMain(payMain);
			par.put(PayMainDao.tablename, payMain);
			par.put(PayMainDao.pay_id, pay_id);
			exestatus=true;
		}else{
			responseStatus=ErrorCode.RESPONSE500;
			responseCode=Integer.parseInt(ErrorCode.ERROR_500);
		}
		
		if(StringUtil.isNullOrEmpty(MapUtils.getString(par, Constant.responseStatus))){
			par.put(Constant.responseStatus, responseStatus);
		}
		if(StringUtil.isNullOrEmpty(MapUtils.getString(par, Constant.responseCode))){
			par.put(Constant.responseCode, responseCode);
		}
		
		if(!exestatus){//插入错误日志
			PayMainError error = new PayMainError(pay_id, Constant.SYS_PAY_CONFIG_CHANNEL_TEMPLATE_TYPE2, JSONUtil.objectToString(par), new Date());
			payMainErrorDao.insertPayMainError(error);
		}

		return par;
	}


	 

	public PayMainDao getPayMainDao() {
		return payMainDao;
	}


	public void setPayMainDao(PayMainDao payMainDao) {
		this.payMainDao = payMainDao;
	}


	public PayMainRetryDao getPayMainRetryDao() {
		return payMainRetryDao;
	}


	public void setPayMainRetryDao(PayMainRetryDao payMainRetryDao) {
		this.payMainRetryDao = payMainRetryDao;
	}

	
	
	
}
