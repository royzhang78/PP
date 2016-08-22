package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Service;
import org.tp.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="testDao") 
public class TestDao extends BaseDao{
	
	
	
	public List query(String sql) throws SException{
		
		 
		return getDbManagement().queryList(sql, new Object[0]);
		
	}
	
	
	
}
