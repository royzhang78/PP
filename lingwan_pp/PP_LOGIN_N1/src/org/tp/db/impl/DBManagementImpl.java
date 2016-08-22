package org.tp.db.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import jodd.log.Logger;
import jodd.log.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;
import org.tp.db.DBManagement;
import org.tp.db.SQLDialect;
import org.tp.exception.SException;

import com.tp.comm.Constant;

public class DBManagementImpl     implements DBManagement{
	
	 

	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DBManagementImpl.class);
	
	private SQLDialect sqlDialect;
	
	private static JdbcTemplate selectTemplate;
	private static JdbcTemplate updateTemplate;
	
	private DataSource queryDataSource;	
	private DataSource updateDataSource;

	public void setQueryDataSource(DataSource queryDataSource) {
		if (null == queryDataSource) {
			logger.error("DataSource is null.");
			return;
		}
		if (null != selectTemplate) {
			try {
				selectTemplate.getDataSource().getConnection().close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		selectTemplate = new JdbcTemplate(queryDataSource);
	}
	
	
	
	
	public DataSource getQueryDataSource() {
		return queryDataSource;
	}




	public DataSource getUpdateDataSource() {
		return updateDataSource;
	}




	public void setUpdateDataSource(DataSource updateDataSource) {
		if (null == updateDataSource) {
			logger.error("DataSource is null.");
			return;
		}
		if (null != updateTemplate) {
			try {
				updateTemplate.getDataSource().getConnection().close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		updateTemplate = new JdbcTemplate(updateDataSource);
	}




	/**
	 * 
	 * @param sqlProc
	 * @param in
	 * @param out<index ,types>
	 * @param beanType
	 * @return
	 */
	public <T> List call(String sqlProc,final Object in[]) {
		logger.info("sql:"+sqlProc);
		final String call = sqlProc;
		List object =  updateTemplate.execute(   
			     new CallableStatementCreator() {   
			        public CallableStatement createCallableStatement(Connection con) throws SQLException {   
			          // String storedProc = "{call testpro(?,?)}";// 调用的sql   
			        
			           CallableStatement cs = con.prepareCall(call);   
//			           cs.setString(1, "p1");// 设置输入参数的值   
//			           cs.registerOutParameter(2, Types.VARCHAR);// 注册输出参数的类型   
			           
			           if(in!=null)
				           for (int i = 0; i < in.length; i++) {
							cs.setObject(i+1,in[i] );
				           }
//				           System.out.println(in[0]);
//				           cs.setObject(1,in[0] );
				       			          
			           return cs;   
			        }   
			     }, new CallableStatementCallback() {   
			        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException {   
			           List resultsMap = new ArrayList();   
//			           cs.execute();   
			           cs.execute(); //执行存储过程
			             
			            /**********返回结果的取什方式，用 cs.getResultSet**********/
			            //如果存储过程最后打开的是游标就直接得到 ResultSet
			            ResultSet rs = cs.getResultSet();
				           while(rs.next()){
				        	  resultsMap.add(MapsUtil.resultSettoMap(rs));
				           } 
//			           }
			           return resultsMap;
				            
			        }   
			  });
		return object;   
	} 
	

	public <T> T queryForBean(String sql, final Class<T> beanType) {
		logger.info("sql:"+sql);
		return (T) selectTemplate.query(sql, new ResultSetExtractor() {

			public T extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				return (T) (rs.next() ? JSONUtil.JsonToObject(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)), beanType) : null);
			}
		});
//		return null;
	}

	public <T> T queryForBean(String sql, final Class<T> beanType,
			Object... args) {
		logger.info("sql:"+sql+"  par:"+JSONUtil.objectToString(args));
		return (T) selectTemplate.query(sql, args, new ResultSetExtractor() {

			public T extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				return (T) (rs.next() ? JSONUtil.JsonToObject(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)), beanType) : null);
			}
		});
//		return null;
	}

	public <T> List<T> queryForBeanList(String sql, final Class<T> beanType) {
		logger.info("sql:"+sql);
		return selectTemplate.query(sql, new RowMapper() {
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object o =JSONUtil.JsonToObject(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)), beanType);
						//(rs.next() ? JSONUtil.JsonToObject(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)), beanType) : null);
				return (T) o;
			}
		});
//		return null;
	}

	public <T> List<T> queryForBeanList(String sql, final Class<T> beanType,
			Object... args) {
		
		logger.info("sql:"+sql+"  par:"+JSONUtil.objectToString(args));
		return selectTemplate.query(sql, args, new RowMapper() {

			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
//				System.out.println(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)));
				return (T) JSONUtil.JsonToObject(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)), beanType);
			}
		});
//		return null;
	}
	
	public  List<Map> queryList(String sql,Object... args) {
		logger.info("sql:"+sql+"  par:"+JSONUtil.objectToString(args));
		return selectTemplate.query(sql, args, new RowMapper() {
			public Map mapRow(ResultSet rs, int rowNum) throws SQLException { 
//				System.out.println(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)));
				return (Map) MapsUtil.resultSettoMap(rs);
			}
		});
	}
	
	
	public <T> List<T> queryForBeanList(String sql, final Class<T> beanType,
			int offset, int limit, Object... args) throws SException {

		DatabaseMetaData d;
		String drivername = "";
		Connection conn = null;
		try {
			if(sqlDialect==null){
				conn = selectTemplate.getDataSource().getConnection();
				d = conn.getMetaData();
				drivername = d.getDriverName();
	
				String[] beanname = Constant.CTX.getBeanDefinitionNames();
				for (String bname : beanname) {
					if (drivername.toLowerCase().contains(bname.toLowerCase())) {
						sqlDialect = (SQLDialect) Constant.CTX.getBean(bname);
						break;
					}
				}
			}
			sql = sqlDialect.getLimitString(sql, offset, limit);
			logger.info("sql:"+sql+"  par:"+JSONUtil.objectToString(args));
			return selectTemplate.query(sql, args, new RowMapper() {

				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					// System.out.println(JSONUtil.objectToString(MapsUtil.resultSettoMap(rs)));
					if (beanType == Map.class) {
						return (T) MapsUtil.resultSettoMap(rs);
					} else {
						return (T) JSONUtil.JsonToObject(JSONUtil
								.objectToString(MapsUtil.resultSettoMap(rs)),
								beanType);
					}
				}
			});
		} catch (SQLException e) {
			logger.error("",e);
			throw new SException();
		} finally {
			
			if (null != conn) {
				
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		 
	}
	
	/**
	 * 执行数据库操作
	 * @throws TaiException
	 */
//	public void execSql() throws TaiException{
//		try{
//			MQmessage o = null;
//			if(Constant.DB_OBJECT.size()>0){ 
//				
//				logger.info("*** 开始执行数据库操作 ***");
//				o = (MQmessage)Constant.DB_OBJECT.remove(0);
//				String sql = o.getSql();
//				Object[] param = o.getO();
//				int result = 0;
//				try{
//					result = jdbcTemplate.update(sql,param);
//				}catch(Exception e){
//					logger.error("*** 执行数据库操作失败 ***",e);
//					Constant.DB_OBJECT.remove(0);
//				}
//				if(result<=0){
//					StringBuffer temp = new StringBuffer();
//					for(int i=0;i<param.length;i++){
//						temp.append(param[i]+" ");
//					}
//					logger.error("*** 执行数据库操作未修改任何行 sql:=***"+sql +" param:"+temp.toString());
//				}
//				else
//					logger.info("*** 执行数据库操作成功 sql:=***"+sql);
//				//Constant.DB_OBJECT.remove(0);
//			}else{
////				logger.info("结束时间"+(i-System.currentTimeMillis()));
//			}
//		}catch(Exception e){
//			logger.error("*** 执行数据库操作失败 ***",e);
//		}
//	}
	/**
	 * 执行查询动作
	 * @param sql
	 * @param o
	 * @return
	 * @throws SException
	 */
	public List searchSql(String sql,Object[] o) throws  SException{
		try{
			logger.info("*** 执行查询动作 sql:=***"+sql);
			return selectTemplate.queryForList(sql, o);
		}catch(Exception e){
			logger.error("*** 执行查询动作失败 ***");
			throw new SException(e);
		}
	}
	
	public int update(String sql,Object[] o) {
		return updateTemplate.update(sql, o);
	}
	 
	
	/****
	 * 查询int值
	 */
//	public int queryForInt (String sql,Object[] o){
//		try{
//			logger.info("*** 获取整形返回值 sql:=***"+sql);
//			List l = null;
//			if(o==null)
//				return selectTemplate.queryForInt(sql);
//			else
//				return selectTemplate.queryForInt(sql,o);
//			
//			 
//		}catch(Exception e){
//			logger.error("*** 获取整形返回值失败 ***");
//			return -1;
//		}
//	}

	public int execSql(String sql) throws SException {
		return updateTemplate.update(sql);
	}
	
	/**
	 * 执行数据库操作
	 * @param message
	 * @throws Exception
	 
	
	public void execSql(MQmessage message) throws SException {
		if (message != null && message.getSqlObjectlist().size() > 0) {
			logger.info("*** 开始执行数据库操作 ***");
			Connection con = DataSourceUtils.getConnection(this.getDataSource());
			PreparedStatement ps = null;
			try {
				int result = 0;
				List<SqlObject> sqllist = message.getSqlObjectlist();
				for (SqlObject obj : sqllist) {
					String sql = obj.getSql();
					Object[] param = obj.getO();
					try{
						ps = (PreparedStatement) con.prepareStatement(sql);
						for (int i = 0; i < param.length; i++) {
							ps.setObject(i + 1, param[i]);
						}
						result = ps.executeUpdate();
					}catch (Exception e) {
						String p = "";
						for (Object s : obj.getO()) {
							p += s + " , ";
						}
						logger.error("sql:" + sql + " param:" + p, e);
						throw new SException(e);
					}
					if (result <= 0) {
						StringBuffer temp = new StringBuffer();
						for (int i = 0; i < param.length; i++) {
							temp.append(param[i] + " ");
						}
						logger.error("*** 执行数据库操作未修改任何行 sql:=***" + sql + " param:" + temp.toString());
					} else {
						// logger.info("*** 执行数据库操作成功 sql:=***" + sql);
					}
				}
			} catch (Exception e) {
				JdbcUtils.closeStatement(ps);
				ps = null;
				DataSourceUtils.releaseConnection(con, this.getDataSource());
				con = null;
				throw new SException(e);
			} finally {
				JdbcUtils.closeStatement(ps);
				DataSourceUtils.releaseConnection(con, this.getDataSource());
			}
		}
	}*/

 
//	public long getSeq(String seqName) {
//		String val = (String) Constant.SEQ_MAP.get(seqName);
//		if(StringUtil.isNullOrEmpty(val)){
//			update(SqlManagement.getprop("insert_seq"),new Object[]{seqName,1});
//			Constant.SEQ_MAP.put(seqName, 1);
//			
//			return 1;
//		}else{
//			long i = Long.valueOf((String) Constant.SEQ_MAP.get(seqName));
//			i++;
//			this.update(SqlManagement.getprop("update_seq"),new Object[]{i,seqName});
//			Constant.SEQ_MAP.put(seqName, i);			
//			return i;
//		}
//	  
//	}



	/**
	 *  
	 * 
	 * 增加并且获取主键
	 * @param sql sql语句
	 * @param params 参数
	 * @return 主键
	 */
	public  long insertAndGetKey(final String sql, final Object in[]) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		updateTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				
				//String sql_sms = "insert into  sms(title,content,date_s,form,sffs,by1,by2,by3) values (?,?,'"+dates+"',?,?,?,?,?)"; 
                PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                if(in!=null)
			           for (int i = 0; i < in.length; i++) {
						ps.setObject(i+1,in[i] );
			           }
                
                return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}




	@Override
	public int queryForInt(String sql, Object[] o) throws SException {
		
		return selectTemplate.queryForObject(sql, o, Integer.class).intValue();
	}
	
	
}
