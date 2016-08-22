package com.rodcell.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.IDGenerate;
import com.rodcell.comm.util.JSPParser;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.RandomUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.SmsOptableDao;
import com.rodcell.dao.TServerPayChannelDao;
import com.rodcell.entity.PayProduct;
import com.rodcell.entity.PayServer;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午2:13:11 
 * 类说明 CashCard统一入口
 */
@Service(value="CashCardCommService")
public class CashCardServiceImpl implements ApiService {
	
	public static Logger log = Logger.getLogger(CashCardServiceImpl.class);

	@Autowired
	private PayProductDao payProductDao;
	
	@Autowired
	private PayServerDao payServerDao;
	
	@Autowired
	private IDGenerate idGenerate;
	
	@Autowired
	private PayMainDao payMainDao;
	
	@Autowired
	private PayMainRetryDao payMainRetryDao;
	@Autowired
	private PayServerChannelDao payServerChannelDao;
	
	@Autowired
	private SmsOptableDao smsOptableDao;
	
	@Autowired
	private TServerPayChannelDao tserverPayChannelDao;
	
	/**
	 * 创建订单流程
	 * @throws SException 
	 */
	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public ReturnObj service(Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SException {
		ReturnObj returnobj=new ReturnObj("");
		String serverName = MapsUtil.getString(par, new String[]{"gameId","gName","serverName"});//游戏服名称
		String uID = MapsUtil.getString(par,"uID");//帐号uid
		String channel_id=MapsUtil.getString(par, new String[]{"payid","channelId"});//支付渠道id
		String productId = MapsUtil.getString(par,"productId");//产品id
		String productName=MapsUtil.getString(par, new String[]{"productName","productId"});//物品名称
		String cparam=MapsUtil.getString(par,"cparam");//自定义字段，充值后返回
		String source=MapsUtil.getString(par,new String[]{"source"});//订单来源 0ppid
//		String test=MapsUtil.getString(par,"test");//订单来源 0pp
		String plmn=MapsUtil.getString(par,"plmn");//获取plmn(如果传值则从服务器获取渠道)
		
		String isSandboxDB=MapsUtil.getString(par,"isSandboxDB");//沙箱数据库模式 如card 则进入测试url地址，数据库模式不变
		String NotifStatus=MapsUtil.getString(par,Constant.NotifStatus);//订单来源 0pp
		
		String unique_key=MapsUtil.getString(par,Constant.unique_key);//唯一 系统传入
		String step1=MapsUtil.getString(par,"step1");
		
		String currency=MapsUtil.getString(par,"currency");//根据物品货币类型确定物品
		
		int cparamlength =0;
		if(cparam!=null){
			cparamlength = StringUtil.stringLength(cparam);
		}
		if(cparamlength>1000){//判断自定义字段长度是否超长
			returnobj.setReturnCode(ErrorCode.PAR_LENGTH_MAX_ERROR);
			toERROR(response, returnobj);
			return returnobj;
		}
		
		if(uID!=null && StringUtil.stringLength(uID)>1000){//判断自定义字段长度是否超长
			returnobj.setReturnCode(ErrorCode.PAR_LENGTH_MAX_ERROR);
			toERROR(response, returnobj);
			return returnobj;
		}
		
		
		PayServer  server = payServerDao.findPayServerByName(serverName);
		if(server==null||server.getS_status()!=Constant.SERVER_STATUS2){//判断服务是否正确
			returnobj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			toERROR(response, returnobj);
			return returnobj;
		}
		
//		PayProduct  product =null;
//		if(!StringUtil.isNullOrEmpty(productName)){//根据物品名称查询
//			product=payProductDao.findPayProductBySidAndName(productName,server.getS_id(),currency);//判断物品是否正确
//		}
//		
//		if(product==null && !StringUtil.isNullOrEmpty(productId)){//根据物品id查询
//			product = payProductDao.findPayProductById(productId);
//		}
//		
//		if(product==null||product.getProduct_status()!=Constant.PRODUCT_STATUS2){
//			returnobj.setReturnCode(ErrorCode.PAYPRODUCT_IS_NULL);
//			
//			toERROR(response, returnobj);
//			return returnobj;			
//		}
		//查询出对应价格的CashCard，若出现多分则出现列表选择
		log.info("=====");
		List<Map> list = payProductDao.findPayProductBySCashCardList(server.getS_id()+"", productName);

		if(list.size()==0){//没有该金额配置
			returnobj.setReturnCode(ErrorCode.CARD_CONFIG_IS_NULL);
			toERROR(response, returnobj);
		}else if(list.size()==1){//唯一点卡数，可直接跳转
			Iterator<String>iterator= par.keySet().iterator();
			String str="?";
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value=String.valueOf(par.get(key));
				str+=key+"="+value+"&";				
			}
			try {
				response.sendRedirect(String.valueOf(list.get(0).get("callurl"))+str);
			} catch (IOException e) {
				log.error("", e);
			}
		}else{//存在多种点卡需要选择
			Iterator<String>iterator= par.keySet().iterator();
			String str="?";
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value=String.valueOf(par.get(key));
				str+=key+"="+value+"&";				
			}
			String returnLanguage="";
			if(list!=null&& list.size()>0){
				returnLanguage=String.valueOf(list.get(0).get("returnLanguage"));
			}
			setdefShowName(list);
			Map httpParaMap = new HashMap();
			httpParaMap.put("urlpar", str);
			httpParaMap.put("showlist", list);
			
			httpParaMap.put("returnLanguage", returnLanguage);
			
			String strValue = JSPParser.getJsp("CashCardComm.tpl", httpParaMap);
			try {
				BaseAction.outString(response,strValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return returnobj;
	}
	
	public void setdefShowName(List<Map> list){//设置默认显示标题
		for (Map m:list) {
			if(m.get("showname")==null || "".equals(m.get("showname"))){
				m.put("showname", m.get("defname"));
			}
			
		}
	}

	
	public static Map getRangeChannel(String plmn, String price,String currency,String channel_Id){
		String channelId=null;
		SmsOptableDao smsOptableDao=(SmsOptableDao)Constant.CTX.getBean("smsOptableDao");
		List<Map> list = smsOptableDao.findPayServerByName(plmn, price, currency,channel_Id);
		if(list==null||list.size()==0){
			return null;
		}
		int i=0;
		int max=0;
		for (Map data:list) {
			max+=MapsUtil.getInteger(data, "percentage");
			i++;
		}
		int num = RandomUtil.Random(1, max);
		
		int tmp1=0;
		int tmp2=0;
		i=0;
		for (Map data:list) {
			tmp2+=MapsUtil.getInteger(list.get(i), "percentage");
			if(tmp2>=num&& num>=tmp1){
				channelId=MapsUtil.getString(data, "c_id");
				return list.get(i);
			}
			tmp1=MapsUtil.getInteger(data, "percentage")+1;	
			i++;
		}
		return null;
	}
	
	public PayProductDao getPayProductDao() {
		return payProductDao;
	}

	public void setPayProductDao(PayProductDao payProductDao) {
		this.payProductDao = payProductDao;
	}

	public PayServerDao getPayServerDao() {
		return payServerDao;
	}

	public void setPayServerDao(PayServerDao payServerDao) {
		this.payServerDao = payServerDao;
	}

	
	public void toERROR(HttpServletResponse response,ReturnObj returnobj){
		Map httpParaMap = new HashMap();
		httpParaMap.put("message", returnobj.getReturnCode());
		String str = JSPParser.getJsp("CashCardError.tpl", httpParaMap);			
		try {
			BaseAction.outString(response,str);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	public static void main(String[] args) {
		List<Map> list =new ArrayList();
		Map m = new HashMap();
		m.put("c_id", "1");
		m.put("range", "2");
		list.add(m);
		
		m = new HashMap();
		m.put("c_id", "2");
		m.put("range", "3");
		list.add(m);
		m = new HashMap();
		m.put("c_id", "3");
		m.put("range", "4");
		list.add(m);
			
		String channelId="";
		if(list==null||list.size()==0){
			return;
		}
		int i=0;
		int max=0;
		for (Map data:list) {
			max+=MapsUtil.getInteger(data, "range");
			i++;
		}
		int num = RandomUtil.Random(1, max);
		
		int tmp1=0;
		int tmp2=0;
		i=0;
		for (Map data:list) {
			tmp2+=MapsUtil.getInteger(list.get(i), "range");
			if(tmp2>=num&& num>=tmp1){
				System.out.println(data);
				channelId=MapsUtil.getString(data, "c_id");
				break;
			}
			tmp1=MapsUtil.getInteger(data, "range")+1;	
			i++;
		}
		System.out.println(num+" "+channelId);  
	}
}
