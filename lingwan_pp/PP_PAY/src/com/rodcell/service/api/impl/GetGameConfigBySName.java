package com.rodcell.service.api.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.TapkGameParamDao;
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
@Service(value="GetGameConfigBySName")
public class GetGameConfigBySName implements ApiService{
	
	@Autowired
	private TapkGameParamDao apkGameParamDao;
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
		String serverName = MapsUtil.getString(map, new String[]{"gName","serverName"});//游戏服名称
		PayServer  server = payServerDao.findPayServerByName(serverName);
		 
		if(server==null){//判断服务是否正确
			obj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			return obj;
		}
		
		obj.setReturnObjs(apkGameParamDao.findAll(server.getS_id()+""));
		return obj;
	}

}
