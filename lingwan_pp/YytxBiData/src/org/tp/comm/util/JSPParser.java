package org.tp.comm.util;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
//import jetbrick.template.utils.UnsafeCharArrayWriter;

import org.springframework.core.io.Resource;

import com.google.common.base.Charsets;
import com.tp.comm.Constant;

public class JSPParser {
	private org.springframework.core.io.Resource[] mappingDirectoryLocations;
	public static Map<String,JetTemplate> JSP_TPL=MapsUtil.newHashMap();
	private JetEngine engine = null;
	
	public void init(){
		Properties prop =new Properties();
		prop.put("compile.path", Constant.APP_ROOT_PATH+"/jsp/jetjspclass");
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
		System.out.println("init JSPParser");
	}
	
	
	private void newBundle(Resource r) throws Exception{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder=factory.newDocumentBuilder();
	    System.out.println(r.getFile().getName());
//	    Document doc = (Document) builder.parse(r.getFile());
	    List<String> list = FilesUtil.readLines(r.getFile(), Charsets.UTF_8);
	    StringBuffer sb = new StringBuffer();
	    for (String s:list) {
			sb.append(s+"\n");
		}
	    JetTemplate template = engine.createTemplate(sb.toString());
	    JSP_TPL.put(r.getFile().getName(),template );
	   
	}


	public org.springframework.core.io.Resource[] getMappingDirectoryLocations() {
		return mappingDirectoryLocations;
	}


	public void setMappingDirectoryLocations(
			org.springframework.core.io.Resource[] mappingDirectoryLocations) {
		this.mappingDirectoryLocations = mappingDirectoryLocations;
		init();
	}

	public static String getJsp(String pageName,Map model){
		JetTemplate template=JSP_TPL.get(pageName);
//		UnsafeCharArrayWriter out = new UnsafeCharArrayWriter();
		 StringWriter out = new StringWriter();
		template.render(model, out);
		return out.toString();
	}
	
}
