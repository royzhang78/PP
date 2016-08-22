package com.rodcell.service.api.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.entity.PayServer;
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
@Service(value="getPayProductById")
public class GetPayProductById implements ApiService{
	
	@Autowired
	private PayProductDao payProductDao;
	@Autowired
	private PayServerDao payServerDao;
	
	/***
	 * 根据服务器名称和物品名查询物品信息
	 */
	@Override
	public ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		ReturnObj obj = new ReturnObj(ErrorCode.NO_ERROR);
		PayServer  server = payServerDao.findPayServerByName(MapsUtil.getString(map, "sName"));
		 
		if(server==null||server.getS_status()!=Constant.SERVER_STATUS2){//判断服务是否正确
			obj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			return obj;
		}
		
		obj.setReturnObjs(payProductDao.findPayProductBySidAndName(MapsUtil.getString(map, "productname"),server.getS_id(),MapsUtil.getString(map, "currency")));
		return obj;
	}

}
