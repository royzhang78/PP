package com.rodcell.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.code.ssm.Cache;
import com.google.code.ssm.api.CacheName;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;
import com.rodcell.comm.MemcachedManager;
import com.rodcell.comm.SqlManagement;
import com.rodcell.comm.SqlXML;
import com.rodcell.dao.BaseDao;
import com.rodcell.entity.TestBean;
import com.rodcell.exception.SException;
import com.rodcell.service.TestDB;
@Service(value="testService") 
public class TestDBImpl extends   BaseDao  implements TestDB {
	
	@Autowired
	private MemcachedManager memcachedManager;

	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public void test1() throws SException {
		Map m = new HashMap();
		m.put("a", "0");
		SqlXML sqlobj =SqlManagement.getpropByXml("sql1", m);
		 this.getDbManagement().update(sqlobj.getSearchsql(), sqlobj.getPar());
		long s =System.currentTimeMillis();
		
		for (int i = 1; i < 10000; i++) {
//			m.put("a", i+"");
			int i1= this.getDbManagement().update(sqlobj.getSearchsql(), new Object[]{i+""});
		}
		System.out.println("run time=="+(System.currentTimeMillis()-s));
//		this.getDbManagement().update("insert into `testtable` (a) values(?)", new Object[]{"c"});
	}
	
	
	private static final String NAMESPACE="ns";
	private Map<String,TestBean> Strings=new HashMap<String,TestBean>();
	
//	@Autowired
//	private Cache defaultMemcachedClient;
	
	
	
	 
	public void saveString(String String) {
//		memcachedManager.cacheObject(String, "1234567890zxcvbnm,./asdfghjkl;'qwertyuiop[]01234567891234567890zxcvbnm,./asdfghjkl;'qwertyuiop[]01234567891234567890zxcvbnm,./asdfghjkl;'qwertyuiop[]01234567891234567890zxcvbnm,./asdfghjkl;'qwertyuiop[]01234567891234567890zxcvbnm,./asdfghjkl;'qwertyuiop[]01234567891234567890zxcvbnm,./asdfghjkl;'qwertyuiop[]0123456789", 0);
		long s = System.currentTimeMillis();
//		for (int i = 0; i < 10000; i++) {
//			System.out.println(memcachedManager.loadObject(String));
		memcachedManager.loadObject(String);
//		}
//		System.out.println((System.currentTimeMillis()-s));
		//Strings.put(String, String);
	}
	/**
	 * 当执行getById查询方法时，系统首先会从缓存中获取StringId对应的实体
	 * 如果实体还没有被缓存，则执行查询方法并将查询结果放入缓存中
	 */
//	@Override
//	//@ReadThroughSingleCache(namespace = NAMESPACE, expiration = 3600)
//	@ReadThroughSingleCache(namespace = "goodscenter:EventGoodsDo", expiration = 60)
//	@CacheName("defaultMemcachedClient")
//	public TestBean getById(@ParameterValueKeyProvider String StringId) {
//		
//		System.out.println(StringId);
//		return Strings.get(StringId);
//	}
//	/**
//	 * 当执行updateString方法时，系统会更新缓存中StringId对应的实体
//	 * 将实体内容更新成@*DataUpdateContent标签所描述的实体
//	 */
//	@UpdateSingleCache(namespace = NAMESPACE, expiration = 3600)
//	@Override
//	@CacheName("defaultMemcachedClient")
//	public void updateString(@ParameterValueKeyProvider @ParameterDataUpdateContent TestBean s) {
//		 
//		Strings.put(s.getId(), s);
//	}
//	/**
//	 * 当执行deleteString方法时，系统会删除缓存中StringId对应的实体
//	 */
//	@InvalidateSingleCache(namespace = NAMESPACE)
//	@Override
//	@CacheName("defaultMemcachedClient")
//	public void deleteString(@ParameterValueKeyProvider String StringId) {
//		Strings.remove(StringId);
//	}


	public MemcachedManager getMemcachedManager() {
		return memcachedManager;
	}


	public void setMemcachedManager(MemcachedManager memcachedManager) {
		this.memcachedManager = memcachedManager;
	}
	

	
}
