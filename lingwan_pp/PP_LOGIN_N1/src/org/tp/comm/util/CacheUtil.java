package org.tp.comm.util;

import java.util.concurrent.TimeUnit;

import org.springframework.mock.web.MockHttpSession;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2015年10月23日 下午4:32:16 
 * 类说明 
 */
public class CacheUtil {
	
	
	
	/**
	 * 
	 * @param expireduration 设置写缓存后 秒钟过期
	 * @param refreshduration 秒中后自动刷新
	 * @param initialCapacity 设置缓存容器的初始容量为10
	 * @param maximumSize 设置缓存最大容量
	 * @param RemovalListener rlistener
	 * @return
	 */
	public static LoadingCache init(long expireduration,long refreshduration,int initialCapacity,long maximumSize,RemovalListener rlistener){
		
		if(rlistener==null){
			rlistener=new RemovalListener<Object, Object>() {
	            @Override
	            public void onRemoval(RemovalNotification<Object, Object> notification) {
	            	 
	            }
			};
		}
		
		
		LoadingCache<String,Object> o //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
        = CacheBuilder.newBuilder()
        //设置并发级别为8，并发级别是指可以同时写缓存的线程数
        .concurrencyLevel(8)
        //设置写缓存后8秒钟过期
        .expireAfterWrite(expireduration, TimeUnit.SECONDS)
        //2秒中后自动刷新
        .refreshAfterWrite(refreshduration, TimeUnit.SECONDS) 
        //设置缓存容器的初始容量为10
        .initialCapacity(initialCapacity)
        //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
        .maximumSize(maximumSize)
        //设置要统计缓存的命中率
//        .recordStats()
        //设置缓存的移除通知
        .removalListener(rlistener)
        //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
        .build(
                new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
//                        System.out.println("load  " + key);
                        return "";
                    }
                }
        );
		return o;
	}
	
	public static Object get(LoadingCache o ,String key){
		Object o1 = o.getUnchecked(key);
		if(o1 instanceof String){
			if("".equals(o1)){
				o.invalidate(key);
				return null;
			}
		}
		return o1;		
	}
	
	public static void put(LoadingCache o ,String key,Object value){
		o.put(key, value);
	}
	
	public static void remove(LoadingCache o ,String key){
		o.invalidate(key);
	}

	
	
	public static void main1(String[] args) {
		MockHttpSession s =new MockHttpSession();
    	s.setMaxInactiveInterval(10);
    	s.setAttribute("a", "333");
//    	System.out.println( s.SessionContext());
    	while (true) { 
			
    	System.out.println(s.getAttribute("a"));
    	try {
			
		 
			Thread.sleep(1000);
//			o.invalidate("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		LoadingCache GameTransactionObj=CacheUtil.init(10, 10, 20, 100, null);
		System.out.println(CacheUtil.get(GameTransactionObj, "1"));
		for (int i = 0; i < 1000; i++) {
			CacheUtil.put(GameTransactionObj, i+"", i+"1111");
		}
		CacheUtil.put(GameTransactionObj, "1", "1111");
		System.out.println(CacheUtil.get(GameTransactionObj, "1"));
		 Thread.sleep(5000);
		 System.out.println(CacheUtil.get(GameTransactionObj, "1"));
		 Thread.sleep(8000);
		 CacheUtil.put(GameTransactionObj, "1", "2222");
		System.out.println(CacheUtil.get(GameTransactionObj, "1"));
		 Thread.sleep(11000);
		 System.out.println(CacheUtil.get(GameTransactionObj, "1"));
		
		LoadingCache<String,Object> o //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
        = CacheBuilder.newBuilder()
        //设置并发级别为8，并发级别是指可以同时写缓存的线程数
        .concurrencyLevel(8)
        //设置写缓存后8秒钟过期
        .expireAfterWrite(8, TimeUnit.SECONDS)
        //2秒中后自动刷新
        .refreshAfterWrite(2, TimeUnit.SECONDS) 
        //设置缓存容器的初始容量为10
        .initialCapacity(10)
        //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
        .maximumSize(100)
        //设置要统计缓存的命中率
//        .recordStats()
        //设置缓存的移除通知
        .removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> notification) {
            	
                System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
            }
        })
        //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
        .build(
                new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        System.out.println("load  " + key);
//                        Student student = new Student();
//                        student.setId(key);
//                        student.setName("name " + key);
                        return "";
                    }
                }
        );
		System.out.println(o.size());
		o.asMap().put("1", "22");
//		try {
//			o.get("1");
//		} catch (ExecutionException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Thread.sleep(1000000);
	
		while (true) { 
			 
//			try {
				
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			try {
				
				 System.out.println("bb6=="+ o.getUnchecked("26").getClass());
				 System.out.println("bb1=="+o.asMap());
				 	System.out.println("bb2=="+o.asMap().size());
				Thread.sleep(1000);
				Thread.sleep(1000);
				Thread.sleep(1000);
				 System.out.println("bb4=="+o.asMap().keySet());
				if(o.asMap().keySet().toArray().length==0){
					
					 System.out.println("bb3=="+o.asMap());
					 o.asMap().put("2", "33");
					 o.invalidate("2");
					 System.out.println("bb5=="+ o.getUnchecked("2"));
				}
				
//				 System.out.println("cache stats:");
//				 Thread.sleep(1000);
//			        //最后打印缓存的命中率等 情况
			     System.out.println(o.stats().toString());
//			     
//			     System.out.println("bb3=="+o.asMap());
//			     if(o.asMap()!=null){
//			    	  System.out.println("bb3=="+o.asMap().get("1"));
//			     }
//				 System.out.println("bb4=="+o.asMap().size());
//				o.invalidate("1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
