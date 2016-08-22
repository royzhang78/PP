package com.rodcell.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.PayProduct;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午2:57:48 
 * 类说明 
 */
@Service(value="payProductDao") 
public class PayProductDao extends BaseDao{

	public static String tablename="PayProduct";
	
	
	public List<PayProduct> findAll(){
		SqlXML sqlxml = getSql("findAllPayProduct", MapsUtil.newHashMap());
		try {
			return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), PayProduct.class);
		} catch (SException e) {
			log.error("", e);
			return null;
		}
	}
	
//	@Override
//	public void init(){
//		List<PayProduct> list =  findAll();
//		for (PayProduct data:list) {
//			this.getMemcachedManager().cacheObject(tablename+data.getProduct_id(), JSONUtil.toJSONObject(data), this.getMemcachedManager().CACHE_EXP_FOREVER);
//			this.getMemcachedManager().cacheObject(tablename+data.getProduct_name(), JSONUtil.toJSONObject(data), this.getMemcachedManager().CACHE_EXP_FOREVER);
//		}
//	}
	
	public PayProduct findPayProductById(String id){
		PayProduct payProduct = null;
		try {
			payProduct =(PayProduct) JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+id),PayProduct.class);
			if(payProduct!=null &&payProduct.getProduct_id()<=0){
				payProduct=null;
			}
		} catch (Exception e) {
//			log.error("",e);
		}
		if(payProduct==null){
			Map m = MapsUtil.newHashMap();
			m.put("product_id", id);
			SqlXML sqlxml = getSql("findPayProductById", m);
			try {
				payProduct = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayProduct.class,sqlxml.getPar());
				if(payProduct!=null){
					this.getMemcachedManager().cacheObject(tablename+payProduct.getProduct_id(), JSONUtil.objectToString(payProduct), this.getMemcachedManager().CACHE_EXP_FOREVER);

					this.getMemcachedManager().cacheObject(tablename+payProduct.getProduct_name()+payProduct.getSid()+payProduct.getProduct_currency(), JSONUtil.objectToString(payProduct), this.getMemcachedManager().CACHE_EXP_FOREVER);
					
				}
			} catch (SException e) {
				log.error("",e);
			}
		}
		return payProduct;
	}
	
	
	public List<Map> findPayProductBySName(String s_name) throws SException{
		Map m = MapsUtil.newHashMap();
		m.put("s_name", s_name);
		SqlXML sqlxml = getSql("findPayProductBySName", m);
		return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
	}
	
	public PayProduct findPayProductBySidAndName(String name,long sid,String currency){
		if(currency==null ||"".equals(currency)){
			currency="THB";
		}
		PayProduct PayProduct = null;
//		try {
//			PayProduct =(PayProduct) JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+name+sid+currency),com.rodcell.entity.PayProduct.class);
//			if(PayProduct!=null &&PayProduct.getProduct_id()<=0){
//				PayProduct=null;
//			}
//		} catch (Exception e) {
////			log.error("",e);
//		}
		if(PayProduct==null){
			Map m = MapsUtil.newHashMap();
			m.put("product_name", name);
			m.put("sid", sid);
			m.put("product_currency", currency);
			SqlXML sqlxml = getSql("findPayProductBySidAndName", m);
			try {
				PayProduct = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayProduct.class,sqlxml.getPar());
				if(PayProduct!=null){
					this.getMemcachedManager().cacheObject(tablename+PayProduct.getProduct_id(), JSONUtil.objectToString(PayProduct), this.getMemcachedManager().CACHE_EXP_FOREVER);
					
					this.getMemcachedManager().cacheObject(tablename+PayProduct.getProduct_name()+PayProduct.getSid()+currency, JSONUtil.objectToString(PayProduct), this.getMemcachedManager().CACHE_EXP_FOREVER);
				}
			} catch (SException e) {
				log.error("",e);
			}
		}
		return PayProduct;
	}
	
	
	
	public List<Map> findPayProductBySCashCardList(String sid,String product_name) throws SException{
		Map m = MapsUtil.newHashMap();
		m.put("sid", sid);
		m.put("product_name", product_name);
		SqlXML sqlxml = getSql("findPayProductBySCashCardList1", m);
		return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
	}
	
	
	
	public List<Map> GetPayProductCofigList(String s_name,String product_name) throws SException{
		Map m = MapsUtil.newHashMap();
		m.put("s_name", s_name);
		m.put("product_name", product_name);
		SqlXML sqlxml = getSql("GetPayProductCofig_SQL", m);
		return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
	}
	
//	public PayProduct findPayProductByName(String name){
//		PayProduct PayProduct = null;
//		try {
//			PayProduct =(PayProduct) JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+name),com.rodcell.entity.PayProduct.class);
//		} catch (Exception e) {
//			log.error("",e);
//		}
//		if(PayProduct==null){
//			Map m = MapsUtil.newHashMap();
//			m.put("product_name", name);
//			SqlXML sqlxml = getSql("findPayProductByName", m);
//			try {
//				PayProduct = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayProduct.class,sqlxml.getPar());
//				if(PayProduct!=null){
//					this.getMemcachedManager().cacheObject(tablename+PayProduct.getProduct_id(), JSONUtil.objectToString(PayProduct), this.getMemcachedManager().CACHE_EXP_FOREVER);
//					
//					this.getMemcachedManager().cacheObject(tablename+PayProduct.getProduct_name(), JSONUtil.objectToString(PayProduct), this.getMemcachedManager().CACHE_EXP_FOREVER);
//				}
//			} catch (SException e) {
//				log.error("",e);
//			}
//		}
//		return PayProduct;
//	}
	


}
