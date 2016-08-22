package org.tp.db.impl;

import org.springframework.stereotype.Service;
import org.tp.db.SQLDialect;

@Service(value="MySQL") 
public class MYSQLServerDialect implements SQLDialect{
	private static final String sqlSelect="select * from (";
	private static final String limit="limit ";
 
	public String getAfterSelect(String sql){
		
		return new StringBuffer(sqlSelect).append(sql).append(" ) as t_"+System.currentTimeMillis()+" ").append(limit).toString();
	}

	public String getLimitString(String querySelect, int offset, int limit) {

//		if (offset > 0) {
//
//			throw new UnsupportedOperationException(
//					"query result offset is not supported");
//
//		}
		String sql =getAfterSelect(querySelect);
		return new StringBuffer(sql).append(" "+offset+","+limit).toString();
//		return new StringBuffer(querySelect.length() + 8)
//
//		.append(querySelect)
//
//		.insert(getAfterSelectInsertPoint(querySelect), " top " + limit)
//
//		.toString();

	}
	
	public static void main(String[] args) {
		String sql= "select * from testtable";
		System.out.println(new MYSQLServerDialect().getLimitString(sql, 1, 10));;
	}

}
