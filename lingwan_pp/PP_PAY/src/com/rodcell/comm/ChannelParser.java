package com.rodcell.comm;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.utils.UnsafeCharArrayWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayChannelDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.SmsSPTableDao;
import com.rodcell.entity.comm.ChannelEntity;

public class ChannelParser {
	private org.springframework.core.io.Resource[] mappingDirectoryLocations;
//	public static Map<String,String> SQLXML=MapsUtil.newHashMap();
	public static JetEngine engine = null;
	public static  Map<String,ChannelEntity> CHANNEL=MapsUtil.newHashMap();
	public static Map<String,JetTemplate> templet=MapsUtil.newHashMap();
	
	
	@Autowired
	public   PayChannelDao payChannelDao;
	
	public  static PayChannelDao tmpDao;
	
	@Autowired
	public SmsSPTableDao smsSPTableDao;
	
	public static SmsSPTableDao tmpsmsSPTableDao;
	
	
	public void init(){
		tmpsmsSPTableDao=smsSPTableDao;
		tmpDao=payChannelDao;
//		PayChannelDao payChannelDao = (PayChannelDao)Constant.CTX.getBean("payChannelDao");
		List<Map> list = payChannelDao.findPayChannel();
		
		Properties prop =new Properties();
		prop.put("compile.path", Constant.APP_ROOT_PATH+"/jetResponseclass");
		engine=JetEngine.create(prop);
		if(mappingDirectoryLocations!=null){
			for (Resource r:mappingDirectoryLocations) {
				try {
					newBundle(r,list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
//			System.out.println(SQLXML);
			 
		}
		System.out.println("init sql");
	}
	
	
	public static void newBundle(Resource r,List<Map> list) throws Exception{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder=factory.newDocumentBuilder();
	    Document doc = (Document) builder.parse(r.getFile());
	    NodeList nl = doc.getElementsByTagName("command");
	    PayChannelDao payChannelDao = null;
	    if(payChannelDao==null)
	    	payChannelDao=ChannelParser.tmpDao;
	    if(payChannelDao==null)
	    	payChannelDao= (PayChannelDao)Constant.CTX.getBean("payChannelDao");
	    
	    for (int i=0;i<nl.getLength();i++){
	    	String json =doc.getElementsByTagName("channel").item(i).getFirstChild().getNodeValue(); 
	    	ChannelEntity channel = (ChannelEntity)JSONUtil.JsonToObject(json, ChannelEntity.class);
	    	
	    	String MOresponseStatus =doc.getElementsByTagName(Constant.MOresponseStatus).item(i).getFirstChild().getNodeValue();
	    	String MOresponse =doc.getElementsByTagName(Constant.MOresponse).item(i).getFirstChild().getNodeValue();
	    	String NotifStatus =doc.getElementsByTagName(Constant.NotifStatus).item(i).getFirstChild().getNodeValue();
	    	String NotifResponse =doc.getElementsByTagName(Constant.NotifResponse).item(i).getFirstChild().getNodeValue();
	    	
	    	CHANNEL.put(channel.getChannen_id()+"", channel);
	    	JetTemplate template = engine.createTemplate(MOresponseStatus);
	    	templet.put(channel.getChannen_id()+Constant.MOresponseStatus, template);
	    	
	    	template = engine.createTemplate(MOresponse);
	    	templet.put(channel.getChannen_id()+Constant.MOresponse, template);
	    	
	    	template = engine.createTemplate(NotifStatus);
	    	templet.put(channel.getChannen_id()+Constant.NotifStatus, template);
	    	
	    	template = engine.createTemplate(NotifResponse);
	    	templet.put(channel.getChannen_id()+Constant.NotifResponse, template);
	    	
	    	SmsSPTableDao smsSPTableDao= tmpsmsSPTableDao;//(SmsSPTableDao)Constant.CTX.getBean("smsSPTableDao");
	    	
	    	int n =0;
	    	for (Map data:list) {
				String channel_id = MapsUtil.getString(data, "channel_id");
				if(channel_id.equals(""+channel.getChannen_id())){
					n=1;
					payChannelDao.updatePayChannel(channel_id, channel.getChannel_name(), channel.getChannel_type()+"");
					if(channel.getChannel_type()==0){
						smsSPTableDao.updateSPTable(channel_id, channel.getChannel_name());
		    		}
				}
			}
	    	if(n==0){
	    		payChannelDao.insertPayChannel(channel.getChannen_id()+"", channel.getChannel_name(), channel.getChannel_type()+"");
	    		if(channel.getChannel_type()==0){
	    			smsSPTableDao.insertSPTable(channel.getChannen_id()+"", channel.getChannel_name());
	    		}
	    		
	    	}
	    	
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


	
	
	public static String toString(String key,Map par){
		JetTemplate template=ChannelParser.templet.get(key);
		UnsafeCharArrayWriter out = new UnsafeCharArrayWriter();
		template.render(par, out);
		if(out==null)
			return null;
		return out.toString();
	}
	
}
