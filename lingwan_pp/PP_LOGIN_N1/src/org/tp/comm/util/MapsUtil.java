package org.tp.comm.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public class MapsUtil {
	public static void main(String[] args) throws InterruptedException {
		Map map=new HashMap();
//		map.put("key", "1");
		int i = getInteger(map, "key");
		System.out.println(i);
		/*ConcurrentMap<String, String> testMap = new MapMaker().concurrencyLevel(1).softKeys().weakValues().expiration( 
                3, TimeUnit.SECONDS).makeComputingMap(new Function<String, String>() { 
            *//** 
             * 这里就是绑定的根据key没找到value的时候触发的function， 
             * 可以将这里的返回值放到对应的key的value中！ 
             * @param arg0 
             * @return 
             *//* 
            @Override 
            public String apply(String arg0) { 
                return "timeOut:"+arg0; 
            } 

        }); 

        testMap.put("a", (new Date()).toLocaleString()); 
        testMap.put("b", new Date().toLocaleString()); 

        System.out.println("a"+testMap.get("a")); 
        System.out.println("b"+testMap.get("b")); 
        System.out.println("c"+testMap.get("c")); 

        *//** 
         * 这里sleep4秒钟过后， 
         * 缓存都失效，再get就会根据绑定的function去获得value放在map中了。 
         *//* 
        try { 
            Thread.sleep(4000); 
            testMap.put("a", (new Date()).toLocaleString()); 
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 

        *//** 
         * 看看这里的再输出，是不是就是新的值了！~ 
         *//* 

        System.out.println("1a"+testMap.get("a")); 
        System.out.println("1b"+testMap.get("b")); 
        System.out.println("1c"+testMap.get("c")); */
		TimeUnit timeUnit= TimeUnit.SECONDS;
		Function function = new Function<String, String>() {
			public String apply(String arg0) {
				System.out.println(arg0);
				return "";
			}

		};
//			Map<String,String> m= newConcurrentMap(3,1, timeUnit, null);
//			m.put("key1", "value1");
//			m.put("key2", "value2");
//			m.put("key3", "value3");
//			m.put("key4", "value4");
//			m.put("key5", "value5");
//			System.out.println("value5:"+m.get("value5"));
			Thread.sleep(2000);
//			System.out.println("value5:"+m.get("value5"));
//			System.out.println(m.size());
////			System.out.println(m.size());
//			System.out.println("1:"+m.get("key2"));
//			Iterator iterator =m.keySet().iterator();
//			while(iterator.hasNext()){
//				String key = (String) iterator.next();
//				System.out.println("key"+key);
//			}
//			System.out.println(m.size());
	}
	
	public static Map<String, Object> resultSettoMap(ResultSet data) throws SQLException{
		Map<String, Object> result = newLinkedHashMap();
		ResultSetMetaData rsmd = data.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			result.put(rsmd.getColumnName(i).trim(), data.getObject(i));
		}

		return result;
	}
	
	/**
	 * 创建HashMap
	 * @return
	 */
	public static Map newHashMap(){
		return Maps.newHashMap();
	}	
	
	public static Map newConcurrentMap(){
		return Maps.newConcurrentMap();
	}
	
	public static Map newLinkedHashMap(){
		return Maps.newLinkedHashMap();
	}
	public static Map newTreeMap(){
		return Maps.newTreeMap();
	}
	
	
	/**
	 * 允许set重复值，可用于该值使用的次数
	 * 如 MyClass myObject = new MyClass();
	 * 	myMultiset.add(myObject);
	 * 	myMultiset.add(myObject);  // add it a second time.		 
	 * 	System.out.println(getMultisetCount(myMultiset，myObject)); // 2
	 * 	myMultiset.remove(myObject);
	 * 	System.out.println(getMultisetCount(myMultiset，myObject)); // 1
	 * @return
	 */
	public static Multiset newMultiset(){
		return HashMultiset.create(); 
	}
	
	/**
	 * 
	 * @param multiset
	 * @param key
	 * @return
	 */
	public static int getMultisetCount(Multiset multiset,Object key){
		return multiset.count(key);
	}
	
	
	/**
	 * 允许重复key值
	 * Multimap<String, String> myMultimap = ArrayListMultimap.create();
	 * myMultimap.put("Fruits", "Bannana"); myMultimap.put("Fruits", "Apple");
	 * myMultimap.put("Fruits", "Pear"); myMultimap.put("Vegetables", "Carrot");
	 * 
	 * int size = myMultimap.size(); System.out.println(size); // 4
	 * Collection<string> fruits = myMultimap.get("Fruits");
	 * System.out.println(fruits); // [Bannana, Apple, Pear]
	 * 
	 * Collection<string> vegetables = myMultimap.get("Vegetables");
	 * System.out.println(vegetables); // [Carrot]
	 * 
	 * for(String value : myMultimap.values()) { System.out.println(value); }
	 * myMultimap.remove("Fruits","Pear");
	 * System.out.println(myMultimap.get("Fruits")); // [Bannana, Pear]
	 * 
	 * myMultimap.removeAll("Fruits");
	 * System.out.println(myMultimap.get("Fruits")); // [] (Empty Collection!)
	 * 
	 * List<string> myValues = myMutlimap.get("Fruits"); //转List
	 * 
	 * @return
	 */
	public static Multimap newMultimap(){
		return ArrayListMultimap.create();  
	}
	
	/**
	 * 双向的Map
	 * @return
	 */
	public static Map newbiMap(){
		return HashBiMap.create();
	}
	
	
	/**
	 * @param  concurrencyLevel并发数量
	 * @param expiredtime  失效时长
	 * @param timeUnit timeUnit.DAYS 按天计算 ，TimeUnit.SECONDS按秒计算
	 * @param Function 超时访问后出发该动作
	 * @return
	 */
//	public static Map newConcurrentMap(int concurrencyLevel,long expiredtime,TimeUnit timeUnit,Function function){
//		
//		if(function != null )
//			return new MapMaker().concurrencyLevel(concurrencyLevel).e.softValues().expiration(expiredtime, TimeUnit.SECONDS).makeComputingMap(function);
//		else 
//			return new MapMaker().concurrencyLevel(concurrencyLevel).softKeys().softValues().expiration(expiredtime, TimeUnit.SECONDS).makeMap();
//	}
	 
	
	/**
	 * 创建static final Map
	 * @param map
	 * @return
	 */
	public static Map newImmutableMap(Map<Object,Object> map){
		ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();  
		if(map !=null && !map.isEmpty()){
			Iterator iterator =map.keySet().iterator();
			while(iterator.hasNext()){
				Object key = iterator.next();
				builder.put(key,(Integer) map.get(key));
			}
		}
		return builder.build();  
	}
	
	
	
	
	public static int getInteger(Map map,String[] keys){
		Object value =null;
		for (String key:keys) {
			Object o = map.get(key);
			if(o!=null){
				value=o;
				break;
			}
		}
		return  Integer.parseInt(String.valueOf(value));
	}
	
	public static int getInteger(Map map,String key){
		Object value = map.get(key);
		return  Integer.parseInt(String.valueOf(value));
	}
	
	
	public static String getString(Map map,String[] keys){
		Object value =null;
		for (String key:keys) {
			Object o = map.get(key);
			if(o!=null){
				value=o;
				break;
			}
		}
		if(value==null){
			return null;
		}
		return  String.valueOf(value);
	}
	public static String getString(Map map,String key){
		Object value = map.get(key);
		if(value==null){
			return null;
		}
		return  String.valueOf(value);
	}
	
	
	public static long getLong(Map map,String[] keys){
		Object value =null;
		for (String key:keys) {
			Object o = map.get(key);
			if(o!=null){
				value=o;
				break;
			}
		}
		return  Long.parseLong(String.valueOf(value));
	}
	public static long getLong(Map map,String key){
		Object value = map.get(key);
		return  Long.parseLong(String.valueOf(value));
	}
	
	
	
	public static double getDouble(Map map,String[] keys){
		Object value =null;
		for (String key:keys) {
			Object o = map.get(key);
			if(o!=null){
				value=o;
				break;
			}
		}
		return  Double.parseDouble(String.valueOf(value));
	}
	public static double getDouble(Map map,String key){
		Object value = map.get(key);
		return  Double.parseDouble(String.valueOf(value));
	}
	
	
	public static float getFloat(Map map,String[] keys){
		Object value =null;
		for (String key:keys) {
			Object o = map.get(key);
			if(o!=null){
				value=o;
				break;
			}
		}
		return   Float.parseFloat(String.valueOf(value));
	}
	public static float getFloat(Map map,String key){
		Object value = map.get(key);
		return  Float.parseFloat(String.valueOf(value));
	}
}
