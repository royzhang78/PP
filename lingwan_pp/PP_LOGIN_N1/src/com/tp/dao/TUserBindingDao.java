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
@Service(value="insertbindingDao") 
public class TUserBindingDao extends BaseDao{
	public static String tablename="";
	
	
	public int insertBinding(Map data) throws SException{
		 
		SqlXML sqlxml = getSql("insertt_user_binding", data);		
		return getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		 
	}
	
	 
	
}
