package com.rodcell.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.SqlManagement;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.BaseDao;
import com.rodcell.dao.PayChannelDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayConfigChannelPriceDao;
import com.rodcell.entity.PayProduct;
import com.rodcell.entity.PayServer;
import com.rodcell.exception.SException;
import com.rodcell.service.UserService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月29日 下午5:22:33 
 * 类说明 
 */
@Service(value="userService") 
public class UserServiceImpl  extends   BaseDao  implements UserService{
	
	@Autowired
	private PayProductDao payProductDao;
	@Autowired
	private PayServerDao payServerDao;
	@Autowired
	private PayServerChannelDao payServerChannelDao;
	
	@Autowired
	private PayChannelDao payChannelDao;
	
	@Autowired
	private SysPayConfigChannelPriceDao sysPayConfigChannelPriceDao;

	@Override
	public Map login(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("user_login", par); 
		return this.getDbManagement().queryForBean(sqlobj.getSearchsql(),Map.class ,sqlobj.getPar());
		 
	}

	@Override
	public List findProduct(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("findProduct", par); 
		return this.getDbManagement().queryForBeanList(sqlobj.getSearchsql(),PayProduct.class ,sqlobj.getPar());
	}

	@Override
	public int findProductCount(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("findProductCount", par); 
		return this.getDbManagement().queryForInt(sqlobj.getSearchsql() ,sqlobj.getPar());
	}
	
	
	
	@Override
	public long insertProduct(Map par) throws Exception {
		String product_currency = MapsUtil.getString(par, "product_currency");
		if(StringUtil.isNullOrEmpty(product_currency)){
			return -501;
		}
		PayProduct p =null;// payProductDao.findPayProductByName(MapsUtil.getString(par, "product_name"),MapsUtil.getLong(par, "sid"),MapsUtil.getString(par, "product_currency"));
		if(p==null){
			return payProductDao.insert(par);
		}
		return -500;
	}
	
	@Override
	public long updateProduct(Map par) throws Exception {
		
		PayProduct p = payProductDao.findPayProductById(MapsUtil.getString(par, "product_id"));
		if(p!=null && p.getProduct_name().equals(MapsUtil.getString(par, "product_name")))//名称不允许修改
			return payProductDao.update(par);
		else
			return -400;
	}
	
	

	
	@Override
	public List findServerByPage(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("findServerByPage", par); 
		return this.getDbManagement().queryForBeanList(sqlobj.getSearchsql(),PayServer.class ,sqlobj.getPar());
	}

	@Override
	public int findServerByPageCount(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("findServerByPageCount", par); 
		return this.getDbManagement().queryForInt(sqlobj.getSearchsql() ,sqlobj.getPar());
	}
	
	
	public PayProductDao getPayProductDao() {
		return payProductDao;
	}

	public void setPayProductDao(PayProductDao payProductDao) {
		this.payProductDao = payProductDao;
	}

	@Override
	public long insertServer(Map par) throws Exception {
		PayServer s =payServerDao.findPayServerByName(MapsUtil.getString(par, "s_name"));
		if(s==null){
			return payServerDao.insert(par);
		}
		return -500;
	}

	@Override
	public long updateServer(Map par) throws Exception {
		// TODO Auto-generated method stub
		PayServer s =payServerDao.findPayServerById(MapsUtil.getString(par, "s_id"));
		if(s!=null && s.getS_name().equals(MapsUtil.getString(par, "s_name"))){//名称不允许修改
			return payServerDao.update(par);
		}
		return -400;
	}

	@Override
	public Map findServerChannel(String sid) throws Exception {
		Map m = MapsUtil.newHashMap();
		m.put("PayServerChannelList", payServerChannelDao.findPayServerChannelBySid(sid));
		m.put("PayChannelList", payChannelDao.findPayChannel());
		return m;
	}

	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public int updateServerChannel(Map par) throws Exception {
		String sid=MapsUtil.getString(par, "s_id");
		payServerChannelDao.delPayServerChannelBySid(sid);
		List<Map> l = (List)par.get("c_id");
		if(l!=null)
		for (Map cid:l) {
			Map schannel=MapsUtil.newHashMap();
			schannel.put("c_id", MapsUtil.getString(cid, "cid"));
			schannel.put("s_id", sid);
			schannel.put("status", 2);
			schannel.put("key", MapsUtil.getString(cid, "key"));
			schannel.put("range", MapsUtil.getString(cid, "range"));
			payServerChannelDao.insertPayServerChannel(schannel);
		}
		return 0;
	}

	@Override
	public Map findconfigChannelPrice(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("findsysPayConfigChannelPriceList", par); 
		Map m=MapsUtil.newHashMap();
		List l = this.getDbManagement().queryForBeanList(sqlobj.getSearchsql(),Map.class ,sqlobj.getPar());
		m.put("ChannelPrice", l);
		m.put("PayChannelList", payChannelDao.findPayChannel());
		return m;
	}

	
	@Override
	public List<Map> findChannelPriceAll(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("findsysPayConfigChannelPriceListAll", par); 
		
		List l = this.getDbManagement().queryForBeanList(sqlobj.getSearchsql(),Map.class ,sqlobj.getPar());
		
		return l;
	}
	
	
	@Override
	public PayProduct findProductById(String id) throws Exception {
		return payProductDao.findPayProductById(id);
	}
	
	@Override
	public int findconfigChannelPriceCount(Map par) throws Exception {
		SqlXML sqlobj =SqlManagement.getpropByXml("findsysPayConfigChannelPriceCount", par); 
		return this.getDbManagement().queryForInt(sqlobj.getSearchsql(), sqlobj.getPar());
	}

	@Override
	public long insertChannelPrice(Map par) throws Exception {
		// TODO Auto-generated method stub
		return sysPayConfigChannelPriceDao.insertsysPayConfigChannelPriceByPrice(par);
	}

	@Override
	public long updateChannelPrice(Map par) throws Exception {
		// TODO Auto-generated method stub
		return sysPayConfigChannelPriceDao.updatesysPayConfigChannelPriceByPrice(par);
	}
	
}
