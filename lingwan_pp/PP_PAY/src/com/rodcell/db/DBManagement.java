package com.rodcell.db;

import java.util.List;
import java.util.Map;

import com.rodcell.exception.SException;

public interface DBManagement {
	
	

	/**
	 * 
	 * @param sqlProc
	 * @param in
	 * @param out<index ,types>
	 * @param beanType
	 * @return
	 */
	public <T> List call(String sqlProc,final Object in[]) throws SException;
	
//	public long getSeq(String seqName);
	public <T> T queryForBean(String sql, final Class<T> beanType) throws SException;
	public <T> T queryForBean(String sql, final Class<T> beanType,
			Object... args) throws SException;
	public <T> List<T> queryForBeanList(String sql, final Class<T> beanType)  throws SException;
	public <T> List<T> queryForBeanList(String sql, final Class<T> beanType,
			Object... args)  throws SException;
	
	public  List<Map> queryList(String sql,Object... args)  throws SException;
	
	
	public <T> List<T> queryForBeanList(String sql, final Class<T> beanType,int offset, int limit, 
			Object... args)  throws SException;
			
	/**
	 * 批量插入
	 * @param message
	 * @throws Exception
	 */
//	public void execSql(MQmessage message) throws SException;
	
	public int update(String sql,Object[] o)throws  SException;

	/**
	 * 执行数据库操作
	 * @throws Exception
	 */
	public int execSql(String sql) throws SException;
	/**
	 * 执行查询动作
	 * @param sql
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public List searchSql(String sql,Object[] o) throws  SException;
	
	
	
	
	
	/**
	 *  查询int值
	 * @param sql
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public int queryForInt (String sql,Object[] o) throws SException;
}
