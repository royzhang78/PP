package com.rodcell.service.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.ListsUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayConfigChannelPriceDao;
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
@Service(value="getPayProductBySName")
public class GetPayProductBySName implements ApiService{
	
	@Autowired
	private PayProductDao payProductDao;
	@Autowired
	private PayServerDao payServerDao;
	@Autowired
	private SysPayConfigChannelPriceDao sysPayConfigChannelPriceDao;
	
	/***
	 * 根据服务器名称和物品名查询物品信息
	 */
	@Override
	public ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		String name=MapsUtil.getString(map, "sName");
		ReturnObj obj = new ReturnObj(ErrorCode.NO_ERROR);
		PayServer  server = payServerDao.findPayServerByName(name);
		 
		if(server==null||server.getS_status()!=Constant.SERVER_STATUS2){//判断服务是否正确
			obj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			return obj;
		}
		
		List<Map> list =payProductDao.findPayProductBySName(name);
		for (Map data:list) {//如果为短信从服务端动态获取支付渠道
			String json = MapsUtil.getString(data, "product_parameter");
			Map product_parameter = JSONUtil.JsonToMap(json);
			if("0".equals(MapsUtil.getString(product_parameter, "type"))){
				List<Map>  returnlist =sysPayConfigChannelPriceDao.findsysPayConfigChannelPriceBYpriceAndCurrency(data);
				String[] array=new String[returnlist.size()];
				int i =0;
				for (Map mapdatta:returnlist) {
					array[i]=(MapsUtil.getString(mapdatta, "channel_type"));
					i++;
				}
				
				product_parameter.put("value", "["+StringUtil.joinSomeStrings(array, ",")+"]");
				String s =JSONUtil.objectToString(product_parameter);
				data.put("product_parameter", StringUtil.replaceAll(s, "\"", "'"));
				
			}
		}
		obj.setReturnObjs(list);
		return obj;
	}

}
