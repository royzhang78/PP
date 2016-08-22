package com.rodcell.service.analyze;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SMSUtil;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月30日 下午3:42:23 
 * 类说明 解析器 用于解析mo传入pay_id参数
 */
@Service(value="SMSAnalyzeService")
public  class SMSAnalyzeServiceImpl implements AnalyzeService{
	
	@Override
	public String service(String text, HttpServletRequest request){
		return SMSUtil.getPayId(text);		 	
	}

}
 
