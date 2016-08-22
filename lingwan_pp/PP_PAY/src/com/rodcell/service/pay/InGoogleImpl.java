package com.rodcell.service.pay;

import java.security.PublicKey;
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
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayServer;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.service.PayMessageService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月6日 下午5:00:15 
 * 类说明 
 */
@Service(value="inGoogleService")
public class InGoogleImpl implements PayMessageService{

	private static Logger logger = Logger.getLogger(InGoogleImpl.class);
	
	
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
	public Map service(long channelid, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SException {
		Map returnMap = MapsUtil.newHashMap();
		ReturnObj returnobj=new ReturnObj(ErrorCode.ERROR_500);
		returnMap.put("responseStatus", ErrorCode.ERROR_500);
		returnMap.put("responseCode", JSONUtil.objectToString(returnobj));
		
		String serverName = MapsUtil.getString(par, new String[]{"gameId","gName","serverName"});//游戏服名称
		PayServer  server = payServerDao.findPayServerByName(serverName);
		if(server==null||server.getS_status()!=Constant.SERVER_STATUS2){//判断服务是否正确
			returnobj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			returnMap.put("ReturnObj", returnobj);
			return returnMap;
		}
		Map datachannel = payServerChannelDao.fingServerChannel(channelid+"", server.getS_id()+"");	
		
		if(datachannel==null || "".equals(MapsUtil.getString(datachannel, "key"))){
			returnobj.setReturnCode(ErrorCode.GOOGLE_CHANNEL_KEY_ERROR);			
			returnobj.setReturnMsg(ErrorCode.GOOGLE_CHANNEL_KEY_ERROR_MSG);
			returnMap.put("ReturnObj", returnobj); 	
			return returnMap;
		}
		PublicKey publicKey = InGoogleUtils.generatePublicKey(MapsUtil.getString(datachannel, "key"));
		
		String signedData = MapsUtil.getString(par, "signedData");
		String signature = MapsUtil.getString(par, "signature");
		if (StringUtil.isNullOrEmpty(signedData) ||! InGoogleUtils.verify(publicKey, signedData, signature)) {
//			logger.info("google signedData error="+JSONUtil.objectToString(par));
			returnobj.setReturnCode(ErrorCode.GOOGLE_VERIFY_ERROR);
			returnobj.setReturnMsg(ErrorCode.GOOGLE_VERIFY_ERROR_MSG);
			returnMap.put("ReturnObj", returnobj);
			// 根据publicKey验证signedData是否和signature保持一致			
			return returnMap;
		}
		
		
//		ReturnObj obj = (ReturnObj)orderService.service(par, request, response, session);
//		PayMain payMain =(PayMain)obj.getReturnValues();
		
		
		ApiService order = (ApiService)Constant.CTX.getBean("orderService");
//		logger.info("google signedData="+signedData);
		Map map = JSONUtil.JsonToMap(signedData);
		int i = MapsUtil.getInteger(map, "purchaseState");
		String PAY_STATUS =Constant.PAY_STATUS_6+"";
		if(i==0){
			PAY_STATUS=Constant.PAY_STATUS_4+"";
		}
		par.put(Constant.NotifStatus, PAY_STATUS);
		par.put("channelId", channelid+"");
		
		//
		par.put("unique_key", MapUtils.getString(map, "orderId"));//保证订单执行一次
//		par.put("isSandboxDB", "isSandboxDB");
		par.put("step1", JSONUtil.objectToString(par));
		ReturnObj robj = (ReturnObj)order.service(par, request, response, session);
		if(robj.getReturnCode().equals(ErrorCode.NO_ERROR)){
			PayMain main = (PayMain)robj.getReturnObjs();	
			returnobj=new ReturnObj(ErrorCode.NO_ERROR);
			returnMap.put("responseStatus", ErrorCode.NO_ERROR);
			returnMap.put("responseCode", JSONUtil.objectToString(main));
		}
		returnMap.put("ReturnObj", robj);
		return returnMap;
	}
	
	

}
