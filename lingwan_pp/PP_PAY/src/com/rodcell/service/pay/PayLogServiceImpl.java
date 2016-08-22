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
import com.rodcell.comm.util.IDGenerate;
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
@Service(value="payLogService")
public class PayLogServiceImpl  implements ApiService{

	private static Logger logger = Logger.getLogger(PayLogServiceImpl.class);
	
	 
	
	@Autowired
	private PayServerDao payServerDao;
	 
	
	@Autowired
	private IDGenerate idGenerate;
	
	@Autowired
	private PayMainDao payMainDao;
	
	
	@Override
	public ReturnObj service(Map par ,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SException{
		ReturnObj returnobj=new ReturnObj(ErrorCode.NO_ERROR);
		
		String status=MapUtils.getString(par, "status");//传入充值成功为5，其余为失败
		String price= MapUtils.getString(par, "price");//支付价格
		String currency= MapUtils.getString(par, "currency");//支付货币单位
		String serverName = MapsUtil.getString(par, new String[]{"gameId","gName","serverName"});//游戏服名称
		String gameRoleId = MapsUtil.getString(par, new String[]{"gameRoleId"});//游戏角色id
		String Product_id=MapsUtil.getString(par, new String[]{"productId"});//游戏物品id
		String serverCode=MapsUtil.getString(par, new String[]{"serverCode"});
		long ppid = 0;
		try{
			ppid = MapsUtil.getLong(par, "ppid");
		}catch(Exception e){
			
		}
		
		PayServer  server = payServerDao.findPayServerByName(serverName);
		if(server==null||server.getS_status()!=Constant.SERVER_STATUS2){//判断服务是否正确
			returnobj.setReturnCode(ErrorCode.PAYSERVER_IS_NULL);
			return returnobj;
		}
		
		
//		if(!(Constant.PAY_STATUS_5+"").equals(status)){
//			status=Constant.PAY_STATUS_6+"";
//		}
		
		String pay_id=Constant.serverkey+""+idGenerate.getId();//创建订单id
		PayMain payMain =new PayMain(pay_id, null, 1,
				Integer.parseInt(status), null,price, currency, 
				new Date(), Constant.FAIL_CODE_0+"", null, null,
				server.getS_id(), null, gameRoleId, Product_id+"", 0, "", null, null, null, null, "", "",server.getIsSandbox(),serverCode);
		payMain.setSource(ppid);
		payMain.setSucccess_pay_date(new Date());
		payMain.setPay_type(Constant.PAY_TYPE1);
		payMainDao.insertPayMain(payMain);
		
		return returnobj;
	}

}
