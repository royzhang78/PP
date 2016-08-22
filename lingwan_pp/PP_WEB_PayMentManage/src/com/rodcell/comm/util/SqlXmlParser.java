package com.rodcell.comm.util;

import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.rodcell.comm.Constant;

public class SqlXmlParser {
	private org.springframework.core.io.Resource[] mappingDirectoryLocations;
	public static Map<String,String> SQLXML=MapsUtil.newHashMap();
	private JetEngine engine = null;
	
	public void init(){
		Properties prop =new Properties();
		prop.put("compile.path", Constant.APP_ROOT_PATH+"/jetsqlclass");
		engine=JetEngine.create(prop);
		if(mappingDirectoryLocations!=null){
			for (Resource r:mappingDirectoryLocations) {
				try {
					newBundle(r);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
//			System.out.println(SQLXML);
			 
		}
		System.out.println("init sql");
	}
	
	
	private void newBundle(Resource r) throws Exception{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder=factory.newDocumentBuilder();
	    Document doc = (Document) builder.parse(r.getFile());
	    NodeList nl = doc.getElementsByTagName("command");
	    
	    for (int i=0;i<nl.getLength();i++){
	    	String value =doc.getElementsByTagName("sql").item(i).getFirstChild().getNodeValue();
	    	String key =doc.getElementsByTagName("sqlname").item(i).getFirstChild().getNodeValue();
	    	SQLXML.put(key, value);
	    	JetTemplate template = engine.createTemplate(value);
	    	SQLParser.sqlMap.put(key+value, template);
//	    	System.out.println(key+":"+value);
	    }
	   
	}


	public org.springframework.core.io.Resource[] getMappingDirectoryLocations() {
		return mappingDirectoryLocations;
	}


	public void setMappingDirectoryLocations(
			org.springframework.core.io.Resource[] mappingDirectoryLocations) {
		this.mappingDirectoryLocations = mappingDirectoryLocations;
		init();
	}


	public Map getSQLXML() {
		return SQLXML;
	}


	public void setSQLXML(Map sQLXML) {
		SQLXML = sQLXML;
	}
	
	
	
}
