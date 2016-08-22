package com.rodcell.db;

public interface SQLDialect {
	public String getLimitString(String querySelect, int offset, int limit) ;
}
