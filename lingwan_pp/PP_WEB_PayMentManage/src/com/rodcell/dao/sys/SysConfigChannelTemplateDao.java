package com.rodcell.dao.sys;

import java.util.List;
import java.util.Map;

import jetbrick.template.JetTemplate;

import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.BaseDao;
import com.rodcell.entity.sys.SysPayConfigChannelTemplate;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月20日 上午11:18:32 
 * 类说明 
 */
@Service(value="sysConfigChannelTemplateDao") 
public class SysConfigChannelTemplateDao extends BaseDao{
	public static String tablename="sysPayConfigChannelTemplate";
	
	public SysPayConfigChannelTemplate selectConfigChannelTemplate(long channleid,int type){
		SysPayConfigChannelTemplate c =null;
		
		c=Constant.SELECT_CONFIG_CHANNEL_TEMPLATE_MAP.get(tablename+channleid+""+type);
			
		if(c==null){
			Map m = MapsUtil.newHashMap();
			m.put("channlelid", channleid);
			m.put("type", type);
			SqlXML sqlxml = getSql("selectConfigChannelTemplatebytype",m );
			try {
				c = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), SysPayConfigChannelTemplate.class,sqlxml.getPar());
				if(c!=null){//读取模版引擎，如果不存在则创建
					Constant.SELECT_CONFIG_CHANNEL_TEMPLATE_MAP.put(tablename+channleid+""+type,c);
					String s ="";
					if(c.getCheckTemplate()!=null){
						s=c.getCheckTemplate();
					}
					JetTemplate template = Constant.engine.createTemplate(s);
			    	Constant.commTemplate.put(tablename+c.getChannel_id()+""+c.getType()+""+Constant.COMM_TEMPLATE_SOURCE_0, template);//初始化CheckTemplate
				
			    	s ="";
					if(c.getEndTemplate()!=null){
						s=c.getEndTemplate();
					}
					JetTemplate template1 = Constant.engine.createTemplate(s);
			    	Constant.commTemplate.put(tablename+c.getChannel_id()+""+c.getType()+""+Constant.COMM_TEMPLATE_SOURCE_1, template1);//初始化EndTemplate
				
			    	s ="";
					if(c.getReturnTemplate()!=null){
						s=c.getReturnTemplate();
					}
					JetTemplate template2 = Constant.engine.createTemplate(s);
			    	Constant.commTemplate.put(tablename+c.getChannel_id()+""+c.getType()+""+Constant.COMM_TEMPLATE_SOURCE_2, template2);//初始化ReturnTemplate
				
				}
			} catch (SException e) {
				log.error("",e);
			}
		}
		return c;
	}
	
	public List<SysPayConfigChannelTemplate> selectConfigChannelTemplate() throws SException{
		List<SysPayConfigChannelTemplate> c =null;
		SqlXML sqlxml = getSql("selectConfigChannelTemplate",MapsUtil.newHashMap() );
		c = this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), SysPayConfigChannelTemplate.class,sqlxml.getPar());
		return c;
	}
	
}
