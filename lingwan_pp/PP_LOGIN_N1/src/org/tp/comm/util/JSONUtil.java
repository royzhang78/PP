package org.tp.comm.util;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 类名称：JSONUtil   
 * 类描述：暂无 
 * 创建人：bin zhang
 * 创建时间：2012-7-5 上午9:49:16   
 * 修改人：bin zhang
 * 修改时间：2012-7-5 上午9:49:16   
 * 修改备注： 
 * @version
 */
public class JSONUtil {
	
	
	/***
	 * 将json解析出来的Map转换为对象
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static Object mapToObject(Map value,Class clazz){
		return JSONUtil.JsonToObject(JSONUtil.objectToString(value),clazz);
	}
	
 
	public static Map objToMap(Object o){
		return (Map) JSONUtil.JsonToObject(JSON.toJSONString(o),HashMap.class);
	}
	
	/**
	 * 将对象转换成json Sting类型
	 * @param o
	 * @return
	 */
	public static String objectToString(Object o){		
		return JSON.toJSONString(o);
	}
	
	
	/**
	 * 将字符串json转换为对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object JsonToObject(String json,Class clazz){
		return JSON.parseObject(json, clazz);
	}
	
	
	/**
	 * 将json转换为map
	 * @param json
	 * @return
	 */
	public static Map JsonToMap(String json){
		return JSON.parseObject(json, HashMap.class);
	}
	
	
	/**
	 * 将json转换为List
	 * @param json
	 * @return
	 */
	public static List JsonToList(String json){
		return JSON.parseArray(json);
	}
	
	public static Object toJSONObject(Object javaObject){
		return JSON.toJSON(javaObject);
	}
	

	
	 
	 
	/** 
	* xml to json <node><key label="key1">value1</key></node> 转化为 
	* {"key":{"@label":"key1","#text":"value1"}} 
	* 
	* @param xml 
	* @return 
	*/ 
//	public static String xmltoJson(String xml) {
//		XMLSerializer xmlSerializer = new XMLSerializer();
//		return xmlSerializer.read(xml).toString();
//	}
	
	/** 
	* map to xml xml <node><key label="key1">value1</key><key 
	* label="key2">value2</key>......</node> 
	* 
	* @param map 
	* @return 
	*/ 
//	public static String jsontoXml(String json) {
//		net.sf.json.JSONObject jobj = net.sf.json.JSONObject.fromObject(json); 
//		System.out.println(jobj.toString());
//		String xml =  new net.sf.json.xml.XMLSerializer().write(jobj); 
//		return xml;
//	}
	
	 
	
	public static void main(String[] args) {
		
		String s="{'key':11,'value':'[40,42]','type':1,'name':'dd"
				+"\n"
				+"\n"
				+ "we'}";
		System.out.println(s);
		Map m = JSONUtil.JsonToMap(s);
		System.out.println(m);
		 
//		Map s =(Map) JSONUtil.JsonToObject(
//			    "<MapSet>"+
//			      "<MapGroup id='Sheboygan'>"+
//			        "<Map>"+
//			          "<Type>MapGuideddddddd</Type>"+
//			          "<SingleTile>true</SingleTile>"+
//			          "<Extension>"+
//			            "<ResourceId>ddd</ResourceId>"+
//			          "</Extension>"+
//			        "</Map>"+
//			        "<Map>"+
//			          "<Type>ccc</Type>"+
//			          "<SingleTile>ggg</SingleTile>"+
//			          "<Extension>"+
//			            "<ResourceId>aaa</ResourceId>"+
//			          "</Extension>"+
//			        "</Map>"+
//			        "<Extension />"+
//			      "</MapGroup>"+
//			      "<ddd>"+
//			      "33333333"+
//			      "</ddd>"+
//			      "<ddd>"+
//			      "444"+
//			      "</ddd>"+
//			    "</MapSet>",Map.class);
//		System.out.println(s);
//		Map  map=JSONUtil.JsonToMap(s);
//		System.out.println(JSONUtil.jsontoXml(s));
			 }
	
}
