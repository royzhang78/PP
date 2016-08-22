package com.rodcell.service.impl;

import java.security.PublicKey;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.rodcell.comm.ConfigKeyUtil;
import com.rodcell.comm.InGoogleUtils;
import com.rodcell.comm.util.MD5Util;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.entity.comm.ChannelDetail;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月6日 上午11:40:39 
 * 类说明 
 */
public class KeyCheckImpl {
	

	private static Logger logger = Logger.getLogger(KeyCheckImpl.class);
	
	
	public static boolean service(ChannelDetail ce, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)  {
		boolean b =false;
		
		if(StringUtil.isNullOrEmpty(ce.getEncryptType())){
			b=true;
		}else{
			String txt = ConfigKeyUtil.KeyReplace(ce.getKey(), par);
			if("md5".equals(ce.getEncryptType())){
				if("42".equals(MapsUtil.getString(par, "pp_paychannelid"))){
					txt=txt.replaceAll(" ", "+");
				}
				String md5=MD5Util.getMD5String(txt);
				String md5par=MapsUtil.getString(par, ce.getKeyName());
				logger.info("md5 check ========="+md5+"  par="+ce.getKeyName()+"  "+md5par +" txt="+txt);
				if(md5.toLowerCase().equals(md5par.toLowerCase())){
					b=true;
				}
			}
		}
		return b;
	}
}
