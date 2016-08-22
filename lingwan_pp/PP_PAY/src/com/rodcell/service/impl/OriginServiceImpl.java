package com.rodcell.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.ChannelParser;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainErrorDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainError;
import com.rodcell.entity.comm.ChannelAnalyze;
import com.rodcell.entity.comm.ChannelEntity;
import com.rodcell.entity.comm.PriceFilter;
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
@Service(value="originService")
public class OriginServiceImpl implements PayMessageService{

	public static Logger log = Logger.getLogger(OriginServiceImpl.class);
	
	@Autowired
	private PayMainDao payMainDao;
	
	@Autowired
	private PayMainErrorDao payMainErrorDao;
	
	/**
	 *  消息确认接口 该接口必须解析出参数中的
	 *  String pay_id;//支付订单id
	 *  String unique_key;//根据渠道id
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
		

		
		List<ChannelAnalyze> list = c.getMo().getAnalyze();
		boolean b = KeyCheckImpl.service(c.getMo(), par, request, response, session);
		if(!b){//如果有消息加密则验证
			responseStatus=ErrorCode.RESPONSE500;
			responseCode=Integer.parseInt(ErrorCode.PAY_KEY_ERROR);		 
			par.put(Constant.responseStatus, responseStatus);
			par.put(Constant.responseCode, responseCode);
			return par;
		}
		
		
		String pay_id=null;
		String unique_key=null;
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
		if(StringUtil.isNullOrEmpty(unique_key)){
			unique_key=pay_id;
		}
		PayMain payMain = payMainDao.findpayMainById(pay_id);
		if(payMain!=null && payMain.getPay_channel_type()==channelid && payMain.getPay_status()==Constant.PAY_STATUS_0){//订单存在并且状态为创建状态  
			PriceFilter  filter = PriceFilterServer.filterPar(channelid, payMain.getPay_money(), payMain.getCurrency(), par);
			if(filter!=null && filter.isStatus()){
				payMain.setUnique_key(unique_key);
				payMain.setStep1(JSONUtil.objectToString(par));
				payMain.setPay_status(Constant.PAY_STATUS_1);
				try{
					
					log.info("c.getOperator()=="+c.getOperator());
					String s[]=StringUtil.split(c.getOperator(), "|");
					String op="";
					Map m = par ; 
					log.info("par=="+par);
					for (int i = 0; i < s.length; i++) {					 
						if(m.get(s[i]) instanceof String){
							op=(String)m.get(s[i]);
						}else if(m.get(s[i]) instanceof Map){
							m=(Map)m.get(s[i]);
						}
					}
					log.info("op=="+op);
					payMain.setOperator(op);
				}catch(Exception e){
					e.printStackTrace();
				}
				payMainDao.updatePayMain(payMain);
				par.put(PayMainDao.tablename, payMain);
				par.put(PriceFilter.PRICEFILTER_NAME, filter);
				par.put(PayMainDao.pay_id, pay_id);
	//			//验证通过执行模版动作
				String sname = c.getMo().getExeServerByServiceName();
				if(!StringUtil.isNullOrEmpty(sname)){
					PayMessageService service = (PayMessageService)Constant.CTX.getBean(sname);
					Map exe = service.service(channelid, par, request, response, session);
					par.put("exePar", exe);
				}
				exestatus=true;
			}else{
				responseStatus=ErrorCode.RESPONSE500;
				responseCode=Integer.parseInt(ErrorCode.PAYPRODUCT_IS_NULL);
			}
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
			PayMainError error = new PayMainError(pay_id, Constant.SYS_PAY_CONFIG_CHANNEL_TEMPLATE_TYPE1, JSONUtil.objectToString(par), new Date());
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

	
	
	
}
