package com.tp.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.tp.comm.SqlManagement;
import org.tp.comm.SqlXML;
import org.tp.comm.util.MapsUtil;
import org.tp.comm.util.SqlXmlParser;
import org.tp.db.DBManagement;
import org.tp.exception.SException;
import org.tp.memcached.MemcachedManager;
 
@Service(value="baseDao") 
public class BaseDao {
	

	public static Logger log = Logger.getLogger(BaseDao.class);
	@Autowired
	private MemcachedManager memcachedManager;
	
	@Autowired
	private DBManagement dbManagement;

	public DBManagement getDbManagement() {
		return dbManagement;
	}

	public void setDbManagement(DBManagement dbManagement) {
		this.dbManagement = dbManagement;
	}
	
	
	
	public MemcachedManager getMemcachedManager() {
		return memcachedManager;
	}

	public void setMemcachedManager(MemcachedManager memcachedManager) {
		this.memcachedManager = memcachedManager;
	}

	public SqlXML getSql(String sqlName,Map pra){
		if(pra==null){
			pra=MapsUtil.newHashMap();
		}
		SqlXML p = SqlManagement.getpropByXml(sqlName, pra);
//		pra.clear();
//		pra=null;
		return p;
	}
	
	public void init() throws SException{
		
	}
	
	public static void main(String[] args) {
		
		ApplicationContext CTX =new FileSystemXmlApplicationContext("E:\\lingwan_workspace\\PP_LOGIN_N1\\src\\config\\spring\\test.xml"); 
		 
		Map data = new HashMap();
		data.put("id", "test22");
		BaseDao b=new BaseDao();
		System.out.println(1);
		while(true){
			SqlXML sqlxml = b.getSql("test_sql_1", data);	
			sqlxml.getSearchsql();
//			sqlxml=null;
		}
		
	}
}
