package com.rodcell.service;

import java.util.List;
import java.util.Map;

import com.rodcell.entity.PayProduct;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月29日 下午5:22:53 
 * 类说明 
 */
public interface UserService {
	public Map login(Map par)throws Exception;
	
	
	public List findProduct(Map par)throws Exception;
	
	public int findProductCount(Map par)throws Exception;


	public long insertProduct(Map par) throws Exception;


	public long updateProduct(Map par) throws Exception;


	public List findServerByPage(Map par) throws Exception;


	public int findServerByPageCount(Map par) throws Exception;
	
	
	public long insertServer(Map par) throws Exception;


	public long updateServer(Map par) throws Exception;
	
	public Map findServerChannel(String sid) throws Exception;
	
	public int updateServerChannel(Map par) throws Exception;
	
	
	public List<Map> findChannelPriceAll(Map par) throws Exception;
	
	public Map findconfigChannelPrice(Map par) throws Exception;
	
	public int findconfigChannelPriceCount(Map par) throws Exception;
	
	
	
	public long insertChannelPrice(Map par) throws Exception;
	
	public long updateChannelPrice(Map par) throws Exception;
	public PayProduct findProductById(String id) throws Exception;
}
