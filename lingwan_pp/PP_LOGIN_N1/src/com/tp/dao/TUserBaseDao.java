package com.tp.dao;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.tp.comm.SqlXML;
import org.tp.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="tUserBaseDao") 
public class TUserBaseDao extends BaseDao{
	public static String tablename="TUserBase";
	
	
	public void insertTUserBaseDao(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("insertTUserBase", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
	 
	
	public void updateTUserBase(Map data) throws SException{
		try{
			SqlXML sqlxml = getSql("updateTUserBase", data);		
			getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		}catch(Exception e){
			log.error("", e);
		}
	}
	
	public int findTUserBaseByPPidAndStatus(Map data) throws SException{		 
		SqlXML sqlxml = getSql("findTUserBaseByPPidAndStatus", data);		
		return getDbManagement().queryForInt(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
	
	
	public Map findTUserBaseByToken(Map data) throws SException{		 
		SqlXML sqlxml = getSql("findTUserBaseByToken", data);		
		return getDbManagement().queryForBean(sqlxml.getSearchsql(),Map.class, sqlxml.getPar());
		
	}
	public int updatet_user_token(Map data) throws SException{		 
		SqlXML sqlxml = getSql("updatet_user_token", data);		
		return getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
	public int insertt_user_token(Map data) throws SException{		 
		SqlXML sqlxml = getSql("insertt_user_token", data);		
		return getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
}
