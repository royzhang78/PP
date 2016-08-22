package com.rodcell.service.pay;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.util.HttpClientUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.exception.SException;
import com.rodcell.service.PayMessageService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月6日 下午3:07:34 
 * 类说明 
 */
@Service(value="timweService")
public class TimweImpl implements PayMessageService{
	

	private static Logger logger = Logger.getLogger(TimweImpl.class);
	
	public static String TimweMTCode="TimweMTCode_";
	private static String replaceAll="+";
	

//	public static final String TimweMTurl="http://mb.timwe.com/sendMT";
	
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	@Autowired
	private PayMainDao payMainDao;

	@Override
	public Map service(long channelid, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		HttpClientUtil http =new HttpClientUtil();
		
		PayMain payMain =(PayMain)par.get(PayMainDao.tablename);
		String Code= (String)sysPayStaticConfigDao.findPayStaticConfigbyKey(TimweMTCode+payMain.getAmount());
		par.put("PricePointId", Code);
		
		
		String partner= (String)sysPayStaticConfigDao.findPayStaticConfigbyKey("TimwePartner");
		String PartnerRoleId= (String)sysPayStaticConfigDao.findPayStaticConfigbyKey("TimwePartnerRoleId");
		
		Map map = getPar(par,partner,PartnerRoleId);
		map.put("PricePointId", Code);
		
		String TimweMTurl=sysPayStaticConfigDao.findPayStaticConfigbyKey("TimweMTurl");
		
		
		try {
			String res = http.post(TimweMTurl, map, "UTF-8");
			payMain.setStep3(res);
			payMainDao.updatePayMain(payMain);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		
		return null;
	}
	
	
	public Map getPar(Map par,String partner,String PartnerRoleId){
		PayMain payMain =(PayMain)par.get(PayMainDao.tablename);
		
		Map map = new HashMap();
		map.put("PartnerRoleId", toString(PartnerRoleId));//
		
		map.put("ProductId",toString(par.get("ProductId")));//
		
		map.put("SenderId","malaysiasms");//
		map.put("CountryId",toString(par.get("CountryId")));//
		map.put("OpId",toString(par.get("OpId")));//
		map.put("Destination",toString(par.get("Origin")));//
		map.put("Text",toString("success"));
		map.put("Test",toString("Test"));
		map.put("ExtTxId",toString(payMain.getPay_id()));//
		map.put("PricePointId", toString(par.get("PricePointId")));
		map.put("PasswordHash", checkArgs(map,partner));
		return map;
	}
	
	
	public static  String checkArgs(Map par,String partner){
		boolean b =false;
		StringBuffer bf=new StringBuffer();
		bf.append("CountryId="+toString(par.get("CountryId"))+"&");//
		bf.append("Destination="+toString(par.get("Destination"))+"&");//
		bf.append("ExtTxId="+toString(par.get("ExtTxId"))+"&");//
		bf.append("OpId="+toString(par.get("OpId"))+"&");//
		bf.append("PartnerRoleId="+toString(par.get("PartnerRoleId"))+"&");//
		bf.append("PricePointId="+toString(par.get("PricePointId"))+"&");
		bf.append("ProductId="+toString(par.get("ProductId"))+"&");//
		bf.append("SenderId="+toString(par.get("SenderId"))+"&");//
		bf.append("Test="+toString(par.get("Test"))+"&");//
		bf.append("Text="+toString(par.get("Text")).replaceAll(" ", replaceAll));
		
		
		String md5= DigestUtils.md5Hex(partner);
		logger.info("check checkArgs===="+partner+"  md5="+md5);
		return md5;
	}
	
	
	public static String toString(Object o){
		if(o==null){
			return "";
		}else{
			return String.valueOf(o).trim();
		}
	}

}
