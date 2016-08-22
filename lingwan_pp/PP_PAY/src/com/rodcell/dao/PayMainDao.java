package com.rodcell.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.PayMain;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="payMainDao") 
public class PayMainDao extends BaseDao{
	public static final String tablename="payMain";
	public static final String pay_id="pay_id";
	public static final String create_pay_date="create_pay_date";
	public static final String unique_key="unique_key";
	public static final String pay_channel_type="pay_channel_type";
	
	 
	public PayMain findpayMainById(String id){
		PayMain payMain = null;
		try {
			payMain =(PayMain)JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+id), PayMain.class);
		} catch (Exception e) {
//			log.error("",e);
		}
		if(payMain==null){
			Map m = MapsUtil.newHashMap();
			m.put(pay_id, id);
			SqlXML sqlxml = getSql("selectPayMainById",m );
			try {
				payMain = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayMain.class,sqlxml.getPar());
				if(payMain!=null)
					this.getMemcachedManager().cacheObject(tablename+payMain.getS_id(), JSONUtil.objectToString(payMain), this.getMemcachedManager().CACHE_EXP_DAY_2);
			} catch (SException e) {
				log.error("",e);
			}
		}
		return payMain;
	}
	
	
	
	
	public PayMain selectPayMainByADColonyOrder(String ppid){
		PayMain payMain = null;

		if(payMain==null){
			Map m = MapsUtil.newHashMap();
			m.put("source", ppid);
			SqlXML sqlxml = getSql("selectPayMainByADColonyOrder",m );
			try {
				payMain = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayMain.class,sqlxml.getPar());
				if(payMain!=null)
					this.getMemcachedManager().cacheObject(tablename+payMain.getS_id(), JSONUtil.objectToString(payMain), this.getMemcachedManager().CACHE_EXP_DAY_2);
			} catch (SException e) {
				log.error("",e);
			}
		}
		return payMain;
	}
	
	/**
	 * 根据支付渠道id获取订单
	 * @param unique_key
	 * @param type
	 * @return
	 */
	public PayMain findpayMainByByunique_keyAndType(String unique_key,String type){
		PayMain payMain = null;
//		try {
//			payMain =(PayMain)JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+id), PayMain.class);
//		} catch (Exception e) {
//			log.error("",e);
//		}
//		if(payMain==null){
			Map m = MapsUtil.newHashMap();
			m.put(PayMainDao.unique_key, unique_key);
			m.put(pay_channel_type, type);
			SqlXML sqlxml = getSql("selectPayMainByunique_keyAndType",m );
			try {
				payMain = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayMain.class,sqlxml.getPar());
				if(payMain!=null)
					this.getMemcachedManager().cacheObject(tablename+payMain.getS_id(), JSONUtil.objectToString(payMain), this.getMemcachedManager().CACHE_EXP_DAY_2);
			} catch (SException e) {
				log.error("",e);
			}
//		}
		return payMain;
	}
	
	public boolean insertPayMain(PayMain payMain){
		if(payMain!=null){
			Map m = JSONUtil.objToMap(payMain);
			SqlXML sqlxml = getSql("insertPayMain",m );
			try {
				int i = this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
				if(i>0){
					this.getMemcachedManager().cacheObject(tablename+payMain.getPay_id(), JSONUtil.objectToString(payMain), this.getMemcachedManager().CACHE_EXP_DAY_2);
					return true;
				}
			} catch (Exception e) {
				new SException(e, "-500", m);
				log.error("",e);
			}
		}
		return false;
	}
	
	
	public boolean updatePayMain(PayMain payMain) throws SException{
		if(payMain!=null){
			Map m = JSONUtil.objToMap(payMain);
			SqlXML sqlxml = getSql("updatePayMain",m );
			
				int i = this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
				if(i>0){
					this.getMemcachedManager().cacheObject(tablename+payMain.getPay_id(), JSONUtil.objectToString(payMain), this.getMemcachedManager().CACHE_EXP_DAY_2);
					return true;
				}
			
		}
		return false;
	}
	
	
}
