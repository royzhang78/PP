package com.rodcell.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.CheckUtil;
import com.rodcell.comm.util.IDGenerate;
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
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.PayProduct;
import com.rodcell.entity.PayServer;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午2:13:11 
 * 类说明  广告模式订单
 */
@Service(value="ADorderService")
public class ADOrderServiceImpl implements ApiService {

//	@Autowired
//	private PayProductDao payProductDao;
	
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
	
//	@Autowired
//	private SmsOptableDao smsOptableDao;
	
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
//		String productId = MapsUtil.getString(par,"productId");//产品id
//		String productName=MapsUtil.getString(par, new String[]{"productName","productId"});//物品名称
		String cparam=MapsUtil.getString(par,"cparam");//自定义字段，充值后返回
		String source=MapsUtil.getString(par,new String[]{"source"});//订单来源 0ppid
//		String test=MapsUtil.getString(par,"test");//订单来源 0pp
//		String plmn=MapsUtil.getString(par,"plmn");//获取plmn(如果传值则从服务器获取渠道)
		
//		String isSandboxDB=MapsUtil.getString(par,"isSandboxDB");//沙箱数据库模式 如card 则进入测试url地址，数据库模式不变
//		String NotifStatus=MapsUtil.getString(par,Constant.NotifStatus);//订单来源 0pp
		
		String unique_key=MapsUtil.getString(par,Constant.unique_key);//唯一 系统传入
		String step1=MapsUtil.getString(par,"step1");
		String currency=MapsUtil.getString(par,"currency");//货币单位
		String money=MapsUtil.getString(par,"money");//金额
		String serverCode=MapsUtil.getString(par,"serverCode");//区服ID
		String productType=MapsUtil.getString(par,"productType");//用户自定义类型
		
		
		int cparamlength =0;
		if(cparam!=null){
			cparamlength = StringUtil.stringLength(cparam);
		}
		if(cparamlength>1000){//判断自定义字段长度是否超长
			returnobj.setReturnCode(ErrorCode.PAR_LENGTH_MAX_ERROR);
			return returnobj;
		}
		
		if(uID!=null && StringUtil.stringLength(uID)>1000){//判断自定义字段长度是否超长
			returnobj.setReturnCode(ErrorCode.PAR_LENGTH_MAX_ERROR);
			return returnobj;
		}
		
		
		
		
		PayServer  server = payServerDao.findPayServerByName(serverName);
		if(server==null||server.getS_status()!=Constant.SERVER_STATUS2){//判断服务是否正确
			returnobj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			return returnobj;
		}
		
		
		Map opt=new HashMap();
		int isSandbox=server.getIsSandbox();
		
		
		
		int status = Constant.PAY_STATUS_0;
//		if(Constant.ISSANDBOX_1==isSandbox && StringUtil.isNullOrEmpty(isSandboxDB)){//0正式模式，1沙箱模式
//			//isSandbox=1;
//			status=Constant.PAY_STATUS_4;
//		}
		if(server.getS_id()==5 ||server.getS_id()==6||server.getS_id()==10){//MT 统一将支付金额设定为一致
			money="1";
		}
		
		String pay_id=Constant.serverkey+""+idGenerate.getId();//创建订单id
		PayMain payMain =new PayMain(pay_id, null, Integer.parseInt(channel_id),
				status, null, money, currency, 
				new Date(), Constant.FAIL_CODE_0+"", null, null,
				server.getS_id(), null, uID, "", 0, step1, null, null, null, null, productType, cparam,isSandbox,serverCode);
		payMain.setAdType(1);//设置为广告模式订单
		
		if(unique_key!=null && !StringUtil.isNullOrEmpty(unique_key)){
			payMain.setUnique_key(unique_key);
		}else{
			payMain.setUnique_key(pay_id);
		}
		payMain.setOpt(opt);
//		payMain.setServer_order_info(product.getProduct_point());
//		payMain.setProductType(product.getProduct_type());
		payMain.setIsSandbox(isSandbox);
		if(!StringUtil.isNullOrEmpty(source)&& CheckUtil.isNum(source)){
			payMain.setSource(Long.valueOf(source));
		}else{
			payMain.setSource(-1);
		}
//		payMain.setProduct(product);
		boolean b = payMainDao.insertPayMain(payMain);//插入数据库
		if(b){
			returnobj.setReturnObjs(payMain);
			returnobj.setReturnCode(ErrorCode.NO_ERROR);
			//6为google支付
//			if((channel_id.equals("6"))||(Constant.ISSANDBOX_1==isSandbox && StringUtil.isNullOrEmpty(isSandboxDB) )||(NotifStatus!=null && NotifStatus.equals(Constant.PAY_STATUS_4))){//0正式模式，1沙箱模式{
//				Date date = new Date();
//				PayMainRetry retry=new PayMainRetry(payMain.getPay_id(), Constant.serverkey+"", date, 
//							0,date, 0, null, null, null, 0);
//				payMainRetryDao.inserPayMainRetry(retry);
//			}
			
			
		}else{
			returnobj.setReturnCode(ErrorCode.ERROR_505);
		}
		return returnobj;
	}

	
	
	
	

	public PayServerDao getPayServerDao() {
		return payServerDao;
	}

	public void setPayServerDao(PayServerDao payServerDao) {
		this.payServerDao = payServerDao;
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
