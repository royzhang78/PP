package com.rodcell.service.api.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.entity.PayProduct;
import com.rodcell.entity.PayServer;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.service.impl.OrderServiceImpl;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月11日 下午3:04:53 
 * 类说明 
 */
@Service(value="SMSOPService")
public class SMSOPService implements ApiService{
	
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
		String productName=MapsUtil.getString(map, "productName");//物品名称
		String serverName = MapsUtil.getString(map, new String[]{"gName","serverName"});//游戏服名称
		String plmn=MapsUtil.getString(map,"plmn");//获取plmn(如果传值则从服务器获取渠道)
		String currency = MapsUtil.getString(map, "currency");
		if(plmn==null ||StringUtil.isNullOrEmpty(plmn)){
			obj.setReturnCode(ErrorCode.PLMN_IS_NULL);
			return obj;
		}
		
		PayServer  server = payServerDao.findPayServerByName(serverName);
		if(server==null||server.getS_status()!=Constant.SERVER_STATUS2){//判断服务是否正确
			obj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			return obj;
		}
		
		PayProduct  product=payProductDao.findPayProductBySidAndName(productName,server.getS_id(),currency);//判断物品是否正确
		
		if(product==null||product.getProduct_status()!=Constant.PRODUCT_STATUS2){
			obj.setReturnCode(ErrorCode.PAYPRODUCT_IS_NULL);
			return obj;
		}
		
		Map data = MapsUtil.newHashMap();
		data.put("opt", OrderServiceImpl.getRangeChannel(plmn,product.getProduct_price(), product.getProduct_currency(),null));
		
		obj.setReturnValues(data);
		return obj;
	}

}
