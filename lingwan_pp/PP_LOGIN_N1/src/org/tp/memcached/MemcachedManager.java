package org.tp.memcached;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Snowolf
 * @version 1.0
 * @since 1.0
 */
@Component 
public class MemcachedManager {
	
	/**
	 * Memcached Client
	 */
	private MemcachedClient memcachedClient=null;
	/**
	 * 缓存时效2小时
	 */
	public static final int CACHE_EXP_2 = 3600 * 2;

	/**
	 * 缓存时效 1天
	 */
	public static final int CACHE_EXP_DAY = 3600 * 24;
	/**
	 * 缓存时效 2天
	 */
	public static final int CACHE_EXP_DAY_2 = 3600 * 24*2;

	/**
	 * 缓存时效 1周
	 */
	public static final int CACHE_EXP_WEEK = 3600 * 24 * 7;

	/**
	 * 缓存时效 1月
	 */
	public static final int CACHE_EXP_MONTH = 3600 * 24 * 30 * 7;

	/**
	 * 缓存时效 永久
	 */
	public static final int CACHE_EXP_FOREVER = 0;

	/**
	 * 冲突延时 1秒
	 */
	public static final int MUTEX_EXP = 1;
	/**
	 * 冲突键
	 */
	public static final String MUTEX_KEY_PREFIX = "MUTEX_";
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MemcachedManager.class);


	
	

	/**
	 * 缓存
	 * 
	 * @param key
	 * @param value
	 * @param exp
	 *            失效时间
	 */
	public void cacheObject(String key, Object value, int exp) {
		if(memcachedClient!=null){
			try {
				memcachedClient.set(key, exp, value);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			logger.info("Cache Object: [" + key + "]");
		}
	}

	/**
	 * Shut down the Memcached Cilent.
	 */
	public void finalize() {
		if (memcachedClient != null) {
			try {
				if (!memcachedClient.isShutdown()) {
					memcachedClient.shutdown();
					logger.debug("Shutdown MemcachedManager...");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 清理对象
	 * 
	 * @param key
	 */
	public void flushObject(String key) {
		if(memcachedClient!=null){
			try {
				memcachedClient.deleteWithNoReply(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			logger.info("Flush Object: [" + key + "]");
		}
	}

	/**
	 * 冲突判定
	 * 
	 * @param key
	 */
	public boolean isMutex(String key) {
		return isMutex(key, MUTEX_EXP);
	}

	/**
	 * 冲突判定
	 * 
	 * @param key
	 * @param exp
	 * @return true 冲突
	 */
	public boolean isMutex(String key, int exp) {
		if(memcachedClient!=null){
			boolean status = true;
			try {
				if (memcachedClient.add(MUTEX_KEY_PREFIX + key, exp, "true")) {
					status = false;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return status;
		}
		return false;
	}

	/**
	 * 加载缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public <T> T loadObject(String key) {
		
		T object = null;
		if(memcachedClient!=null){
			try {
				object = memcachedClient.<T> get(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			logger.info("Load Object: [" + key + "]");
		}
		return object;
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	
	
	

}