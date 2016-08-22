package com.rodcell.server.http;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rodcell.comm.Constant;
import com.rodcell.dao.BaseDao;
import com.rodcell.dao.sys.SysPayConfigChannleFilterDao;
import com.rodcell.dao.sys.SysPayConfigfilterDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.sys.SysPayconfigchannelfilter;
import com.rodcell.entity.sys.SysPayconfigfilter;
import com.rodcell.exception.SException;

public class AppInit extends BaseDao{
	 
	/**
	 * 初始化
	 * @throws SException 
	 */
	@Override
	public void init() throws SException {			
		
		initPayStaticConfig();
//		initsysPayconfigfilterDao();
//		initsysPayconfigchannelfilter();
		//initTask();
	}
	 
	 
	
	public void initPayStaticConfig() throws SException{
		SysPayStaticConfigDao sysPayStaticConfigDao=(SysPayStaticConfigDao)Constant.CTX.getBean("sysPayStaticConfigDao");
		sysPayStaticConfigDao.findALL();
	}
	
	/*****
	 * 初始化过滤器模版
	 * @throws SException
	 */
//	public void initsysPayconfigfilterDao() throws SException{
//		SysPayConfigfilterDao sysPayconfigchannelfilterDao=(SysPayConfigfilterDao)Constant.CTX.getBean("sysPayconfigfilterDao");
//		List<SysPayconfigfilter>  list =sysPayconfigchannelfilterDao.selectConfigchannelfilter();
//		for (SysPayconfigfilter c:list) {
//			sysPayconfigchannelfilterDao.selectConfigchannelfilterByfid(c.getFid());
//		}
//	}
	
	
	/*****
	 * 初始化过滤器模版
	 * @throws SException
	 */
//	public void initsysPayconfigchannelfilter() throws SException{
//		SysPayConfigChannleFilterDao sysPayConfigChannleFilterDao=(SysPayConfigChannleFilterDao)Constant.CTX.getBean("sysPayconfigchannelfilterDao");
//		List<SysPayconfigchannelfilter>  list =sysPayConfigChannleFilterDao.selectConfigchannelfilter();
//		for (SysPayconfigchannelfilter c:list) {
//			Constant.SELECT_SYS_PAY_CONFIG_CHANNEL_FILTER_MAP.put(c.getId()+"", c);
//		}
//	}
	
	
}
