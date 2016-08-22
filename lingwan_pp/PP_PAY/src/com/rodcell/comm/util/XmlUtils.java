package com.rodcell.comm.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtils {

	/**
	 * 将xml转换为map
	 * 
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> Dom2Map(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * 将map转换为xml
	 * 
	 * @param map
	 * @param iMode
	 * @return
	 */
	public static String GetXMLFromMap(Map map, int iMode) {
		StringBuffer sb = new StringBuffer();

		if (map == null || map.size() == 0) {
			return "";
		}
		if (0 == iMode) {
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			// sb.append("<root>\n");
		}
		// 为了方便观看结果,将有内嵌的排在后面

		Iterator iterr = map.keySet().iterator();

		while (iterr.hasNext()) {
			String keyString = (String) iterr.next();
			if (!(map.get(keyString) instanceof Map)) {
				sb.append("<" + keyString + ">");
				sb.append(map.get(keyString));
				sb.append("</" + keyString + ">\n");
			}
		}
		iterr = map.keySet().iterator();
		while (iterr.hasNext()) {
			String key = (String) iterr.next();
			if (map.get(key) instanceof Map) {
				if (key.indexOf("|!|") > 0) {
					sb.append("<" + key.substring(0, key.indexOf("|!|"))
							+ ">\n");
					sb.append(GetXMLFromMap((Map) map.get(key), iMode + 1));
					sb.append("</" + key.substring(0, key.indexOf("|!|"))
							+ ">\n");
				} else {
					sb.append("<" + key + ">\n");
					sb.append(GetXMLFromMap((Map) map.get(key), iMode + 1));
					sb.append("</" + key + ">\n");
				}
			}
		}
		// if (0 == iMode)
		// sb.append("</root>\n");
		return sb.toString();
	}

	public static void main(String[] args) throws IOException,
			DocumentException {

		FileInputStream fis = new FileInputStream("d://a.xml");
		byte[] b = new byte[fis.available()];
		fis.read(b);
		String str = new String(b);

		Document doc = DocumentHelper.parseText(str);

		System.out.println(doc.asXML());
		long beginTime = System.currentTimeMillis();

		Map<String, Object> map = XmlUtils.Dom2Map(doc);
		System.out.println(JSONUtil.objectToString(map));

		System.out.println();
		Map r = new HashMap();
		r.put("set", map);
		String result = GetXMLFromMap(r, 0);
		System.out.println(result);

		System.out.println("Use time:"
				+ (System.currentTimeMillis() - beginTime));
	}
} 
