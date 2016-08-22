package com.rodcell.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.CheckUtil;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.sys.SysPayConfigChannleFilterDao;
import com.rodcell.dao.sys.SysPayConfigfilterDao;
import com.rodcell.entity.FilterObject;
import com.rodcell.entity.sys.SysPayconfigchannelfilter;
import com.rodcell.entity.sys.SysPayconfigfilter;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月22日 下午2:44:19 
 * 类说明 
 */
public class FilterServer {
	

	public static Logger log = Logger.getLogger(FilterServer.class);
	
	
	/**
	 * 支付流程参数过滤
	 * @param channel_id
	 * @param type
	 * @param par
	 * @return
	 * @throws SException 
	 */
	public static FilterObject payfilterPar(long channel_id,String type,Map par ) throws SException{
		SysPayConfigChannleFilterDao c = (SysPayConfigChannleFilterDao)Constant.CTX.getBean("sysPayconfigchannelfilterDao");
		List<SysPayconfigchannelfilter> flist = c.selectConfigchannelfilterByChannelIdtype(channel_id, type);//查询出对应的配置信息
		SysPayConfigfilterDao spcfd = (SysPayConfigfilterDao)Constant.CTX.getBean("sysPayconfigfilterDao");
		FilterObject o = new FilterObject();
		for (SysPayconfigchannelfilter filter : flist) {
			SysPayconfigfilter cf = spcfd.selectConfigchannelfilterByfid(filter
					.getFid());
			String value = MapsUtil.getString(par, cf.getColname());// 获取参数对象
			if (value == null) {
				value = "";
			}

			int _len = StringUtil.stringLength(value);
			boolean b = true;
			if (_len >= cf.getMinlength() && _len <= cf.getMaxlength()) {// 验证参数长度
				b = true;
			} else {
				b = false;
			}
			if (b) {
				if (!StringUtil.isNullOrEmpty(cf.getRegEx())) {
					b = CheckUtil.checkPattern(cf.getRegEx(), value);// 验证正则表达式
					if (!b) {
						log.error("FilterServer.payfilterPar key="
								+ cf.getColname() + "  value=" + value
								+ "  checkPattern = " + cf.getRegEx()
								+ " response_status="
								+ cf.getError_response_status() + " ReturnTxt="
								+ cf.getReturnTxt() +" par=="+JSONUtil.objectToString(par));// 验证的表达式
					}
				}
			} else {
				log.error("FilterServer.payfilterPar key=" + cf.getColname()
						+ "  value=" + value + "  checklength min="
						+ cf.getMinlength() + " max=" + cf.getMaxlength()
						+ " response_status=" + cf.getError_response_status()
						+ " ReturnTxt=" + cf.getReturnTxt()+" par=="+JSONUtil.objectToString(par));// 验证的长度
			}
			o.setStatus(b);

			if (!b) {
				o.setResponseStatus(cf.getError_response_status());// 验证失败则返回状态
				o.setReturnTxt(cf.getReturnTxt());// 验证失败则返回字符串
			}

		}
		return o;
	}

}
