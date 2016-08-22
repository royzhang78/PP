package com.rodcell.service.pay;

import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.InGoogleUtils;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MD5Util;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.PayServer;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.service.PayMessageService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * 适用客户端充值完毕后通知pp 支付系统
 * @version 创建时间：2014年6月6日 下午5:00:15 
 * 类说明 
 */
@Service(value="ggtdpService")
public class GGTDPServiceImpl  implements ApiService{

	private static Logger logger = Logger.getLogger(GGTDPServiceImpl.class);
	
	
	@Autowired
	private ApiService orderService;
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	@Autowired
	private PayServerChannelDao payServerChannelDao;
	

	@Autowired
	private PayServerDao payServerDao;
	

	@Autowired
	private PayMainRetryDao payMainRetryDao;
	
	
	@Override
//	public Map service(long channelid, Map par, HttpServletRequest request,
//			HttpServletResponse response, HttpSession session) throws SException {
	public ReturnObj service(Map par ,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SException{
		long channelid=47;
		Map returnMap = MapsUtil.newHashMap();
		ReturnObj returnobj=new ReturnObj(ErrorCode.ERROR_500);
		returnMap.put("responseStatus", ErrorCode.ERROR_500);
		returnMap.put("responseCode", JSONUtil.objectToString(returnobj)); 
//		String serverName = MapsUtil.getString(par, new String[]{"gName","serverName"});//游戏服名称
		String orderid = MapsUtil.getString(par, new String[]{"orderid"});//游戏服名称
		String verify = MapsUtil.getString(par, new String[]{"verify"});//验证key
		int status = MapsUtil.getInteger(par, new String[]{"status"});//状态
		int fail_code=status;
		
			

		PayMainDao dao =(PayMainDao)Constant.CTX.getBean("payMainDao");
		
		PayServerDao payServerDao =(PayServerDao)Constant.CTX.getBean("payServerDao");
		PayProductDao payProductDao =(PayProductDao)Constant.CTX.getBean("payProductDao");		
		PayMain main = dao.findpayMainById(orderid);
		if(main==null){
			returnobj.setReturnCode(ErrorCode.ORDER_ISNULL_ERROR);			
			returnobj.setReturnMsg(ErrorCode.ORDER_ISNULL_ERROR_MSG);
			returnMap.put("ReturnObj", returnobj); 	
			return returnobj;
		}
		
		if(main.getPay_status()!=Constant.PAY_STATUS_0){
			returnobj.setReturnCode(ErrorCode.ORDER_STATUS_ERROR);			
			returnobj.setReturnMsg(ErrorCode.ORDER_STATUS_ERROR_MSG);
			returnobj.setReturnObjs(main);
			returnMap.put("ReturnObj", returnobj); 	
			return returnobj;
		}
		if("0".equals(status+"")){//0表示充值完成
			status=Constant.PAY_STATUS_4;
		}else{
			status=Constant.PAY_STATUS_6;
		}
		main.setPay_status(status);//更新订单状态
		main.setFail_code(fail_code+"");
		
		PayServer  ps = payServerDao.findPayServerById(main.getS_id()+"");
		
		
		Map datachannel = payServerChannelDao.fingServerChannel(channelid+"", ps.getS_id()+"");	
		String key ="";
		if(datachannel!=null)
			key=MapsUtil.getString(datachannel, "key");//获取验证密钥
		if(key==null){
			key="";
		}
		 
		if(verify==null||!MD5Util.getMD5String(orderid+ps.getS_name()+key).toLowerCase().equals(verify.toLowerCase())){
			returnobj.setReturnCode(ErrorCode.CHANNEL_VERIFY_ERROR);			
			returnobj.setReturnMsg(ErrorCode.CHANNEL_VERIFY_ERROR_MSG);
			returnMap.put("ReturnObj", returnobj); 	
			return returnobj;
		}
		dao.updatePayMain(main);
		
		if("0".equals(status+"")){//0表示充值完成
			Date date = new Date();
			PayMainRetry retry=new PayMainRetry(main.getPay_id(), Constant.serverkey+"", date, 
						0,date, 0, null, null, null, 0);
			payMainRetryDao.inserPayMainRetry(retry);
		}
		
		returnobj.setReturnCode(ErrorCode.NO_ERROR);
		returnobj.setReturnObjs(main);
		returnMap.put("ReturnObj", returnobj);
		return returnobj;
	}
	
	

}
