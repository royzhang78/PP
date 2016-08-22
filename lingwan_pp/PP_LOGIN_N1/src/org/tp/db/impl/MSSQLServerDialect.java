package org.tp.db.impl;

import org.springframework.stereotype.Service;
import org.tp.db.SQLDialect;

@Service(value="Microsoft") 
public class MSSQLServerDialect implements SQLDialect{
	private static final String sqlSelect="select * from (select ROWSID=ROW_NUMBER()OVER(";
	private static final String orderbyToSQL="order by (select 1 value)";
	private static final String bodystartsql="* FROM( select ";
	private static final String bodyendsql=" ) AS tmptable2";
 
	public String getAfterSelect(String sql){
		int selectIndex = sql.toLowerCase().indexOf("select");
//		String delSelect =new StringBuffer(sql).substring(selectIndex+6, sql.length());
		String orderbySql="";
		String bodySql=new StringBuffer(sql).substring(selectIndex+6, sql.length());
		bodySql= bodystartsql+bodySql ;
		selectIndex = bodySql.toLowerCase().indexOf("order");
		if(selectIndex>=0){
			orderbySql=new StringBuffer(bodySql).substring(selectIndex, bodySql.length());
			bodySql=new StringBuffer(bodySql).substring(0, selectIndex);
		}else{
			orderbySql=orderbyToSQL;
//			bodySql=sql;
		}
		bodySql=bodySql+bodyendsql; 
//		final String selectDistinct = sql.toLowerCase();
		return new StringBuffer(sqlSelect).append(orderbySql).append(" ),").append(bodySql).append(") as tmptable").toString();
	}

	public String getLimitString(String querySelect, int offset, int limit) {

//		if (offset > 0) {
//
//			throw new UnsupportedOperationException(
//					"query result offset is not supported");
//
//		}
		String sql =getAfterSelect(querySelect);
		return new StringBuffer(sql.length() + 8).append(sql+" where tmptable.ROWSID >="+offset+" and tmptable.ROWSID<="+limit).toString();
//		return new StringBuffer(querySelect.length() + 8)
//
//		.append(querySelect)
//
//		.insert(getAfterSelectInsertPoint(querySelect), " top " + limit)
//
//		.toString();

	}
	
	public static void main(String[] args) {
		String sql= "SELECT  [test]   ,[createdate],(select v1 from [Unilever].[dbo].[res] where rownames=test) as val   FROM [Unilever].[dbo].[testtable]";
		System.out.println(new MSSQLServerDialect().getLimitString(sql, 1, 10));;
	}

}
