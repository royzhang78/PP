package com.rodcell.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainError;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="payMainErrorDao") 
public class PayMainErrorDao extends BaseDao{
	public static final String tablename="payMainError";
	
	
	public boolean insertPayMainError(PayMainError payMainerror){
		if(payMainerror!=null){
			Map m = JSONUtil.objToMap(payMainerror);
			SqlXML sqlxml = getSql("insertPayMainError",m );
			try {
				int i = this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
				
			} catch (Exception e) {
				log.error("",e);
			}
		}
		return false;
	}
	
	 
}
