package com.rodcell.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.MemcachedManager;
import com.rodcell.comm.SqlManagement;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.db.DBManagement;
import com.rodcell.exception.SException;
 
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
		return SqlManagement.getpropByXml(sqlName, pra);
	}
	
	public void init() throws SException{
		
	}
}
