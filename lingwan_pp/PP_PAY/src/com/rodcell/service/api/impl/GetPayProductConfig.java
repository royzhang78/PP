package com.rodcell.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.rodcell.dao.TServerPayChannelDao;
import com.rodcell.dao.sys.SysPayConfigChannelPriceDao;
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
@Service(value="GetPayProductConfig")
public class GetPayProductConfig implements ApiService{
	
	@Autowired
	private PayProductDao payProductDao;
	@Autowired
	private PayServerDao payServerDao;
	@Autowired
	private SysPayConfigChannelPriceDao sysPayConfigChannelPriceDao;
	
	
	@Autowired
	private TServerPayChannelDao tserverPayChannelDao;
	/***
	 * 根据服务器名称和物品名查询物品信息
	 */
	@Override
	public ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		String name=MapsUtil.getString(map, new String[]{"gName","sName"});
		String productId=MapsUtil.getString(map, new String[]{"productName","productId"});
		String plmn =MapsUtil.getString(map, "plmn"); 
		
		
		ReturnObj obj = new ReturnObj(ErrorCode.NO_ERROR);
		List<Map> list = payProductDao.GetPayProductCofigList(name,productId);
		Map<String,List> currencyMap = new HashMap();
		if(list!=null){
			for (Map product:list) {
				product.put("productID", product.get("product_name"));
				product.remove("product_id");
				if("0".equals(MapsUtil.getString(product, "pay_channel_type"))){//获取短信支付方式
					String currency_str= MapsUtil.getString(product, "product_currency");
					if(currencyMap.get(currency_str)==null){//当货币单位不同则创建新list添加
						List clist = new ArrayList();
						clist.add(product);
						currencyMap.put(currency_str, clist);
					}else{
						List clist =currencyMap.get(currency_str);
						clist.add(product);
						currencyMap.put(currency_str, clist);
					}
				}
			}
		}
		
		
		String channel_id = tserverPayChannelDao.findPayServerByName(name);//可以指定服务器使用支付渠道。方便测试

		List smsOpt=new ArrayList();
		
		Iterator<String> it=currencyMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			List l =currencyMap.get(key);
			Map m = (Map)l.get(0);
			Map opt = OrderServiceImpl.getRangeChannel(plmn, MapsUtil.getString(m, "product_price"),
					MapsUtil.getString(m, "product_currency"), channel_id);
			if(opt!=null){
				smsOpt.add(opt);
			}
		}
		
		PayServer  server = payServerDao.findPayServerByName(name);
		
//		List<Map> CashCardlist =new ArrayList();
//		if (server!=null){
//			CashCardlist = payProductDao.findPayProductBySCashCardList(server.getS_id()+"", productId);
//		}
		
		
		
		Map data=new HashMap();
		data.put("productList", list);
//		data.put("smsOptList", smsOpt);
//		data.put("CashCardlist", CashCardlist);
		
		
		
		obj.setReturnObjs(data);
		return obj;
	}

}
