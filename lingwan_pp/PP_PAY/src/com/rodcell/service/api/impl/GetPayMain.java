package com.rodcell.service.api.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月11日 下午3:04:53 
 * 类说明 
 */
@Service(value="GetPayMain")
public class GetPayMain implements ApiService{
	
	@Autowired
	private PayMainDao payMainDao;
	
	/***
	 * 根据服务器名称和物品名查询物品信息
	 */
	@Override
	public ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		String payid=MapsUtil.getString(map, "payid");
		ReturnObj obj = new ReturnObj(ErrorCode.NO_ERROR);
		PayMain main = payMainDao.findpayMainById(payid);
		if(main==null){
			obj.setReturnCode(ErrorCode.PAY_MAIN_IS_NULL);
		}
		obj.setReturnObjs(main);
		return obj;
	}

}
