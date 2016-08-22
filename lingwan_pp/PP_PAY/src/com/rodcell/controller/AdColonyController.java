package com.rodcell.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.HttpClientUtil;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class AdColonyController extends BaseAction{
	
	@Autowired
	private ApiService AdColonyService;
	 
	public static Logger log = Logger.getLogger(AdColonyController.class);
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/CallAdColony")  
    public synchronized String CallAdColony(HttpServletRequest request, HttpServletResponse response, HttpSession session) { 
//		Map map=new HashMap();
		try {
			Map map=getPar(request);
			log.info(JSONUtil.objectToString(map));
			
			ReturnObj  r = AdColonyService.service(map, request, response, session);
			if(r.getReturnCode().equals(ErrorCode.NO_ERROR)){
				outString(response, "vc_success");
			}else{
				outString(response, "vc_decline");
			}
			r=null;
			map.clear();
			map=null;
			return null;
		} catch (Exception e) {		
			
//			log.info("apiController.class  parameter=="+JSONUtil.objectToString(map));
			
			log.error("", e);
			try {
				this.outString(response, JSONUtil.objectToString(new ReturnObj(ErrorCode.ERROR_500+"")));
			} catch (Exception e1) {
				
			}
			response.setStatus(500);
			return null;
		}
		
    }
	
	 
	
  
		
}
