package com.tp.dao;

import java.util.HashMap;
import java.util.List;
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
@Service(value="loginLogDao") 
public class LoginLogDao extends BaseDao{
	public static String tablename="LoginLog";
	
	
	
	
	
	public void createloginlogDao(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("createloginlog", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	public void insertTUserBaseDao(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("insertloginlog", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
	
	
	public void insertTUserFaceBook(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("insertTUserFacebook", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
	
	
	public void inserttuserfacebookdata(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("inserttuserfacebookdata", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
	
	public void deltuserfacebookdata(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("deltuserfacebookdata", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
	
	
	public List<Map> findtuserfacebookdata() throws SException{
		Map m = new HashMap();
		SqlXML sqlxml = getSql("findtuserfacebookdata", m);	
		m=null;
		return getDbManagement().queryList(sqlxml.getSearchsql(), sqlxml.getPar());
	}
	
	
	public void updateTUserFacebook(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("updateTUserFacebook", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
	}
	
}
