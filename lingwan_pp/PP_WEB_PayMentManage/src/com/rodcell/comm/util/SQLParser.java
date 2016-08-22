package com.rodcell.comm.util;

import java.io.StringWriter;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.utils.UnsafeCharArrayWriter;

import org.apache.log4j.Logger;

import com.rodcell.comm.SqlXML;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class SQLParser {
	
	private static Logger logger = Logger.getLogger(SQLParser.class);
	public static Map<String,JetTemplate> sqlMap=MapsUtil.newHashMap();
	public static JetEngine engine = JetEngine.create();
	public static SqlXML getSql(String propName,String sql, Map<String, Object> model) {
		SqlXML sxml=new SqlXML();
		try {
//			String key = sql + JSONUtil.objectToString(model.keySet())
//					+ JSONUtil.objectToString(model.values());

			if(sqlMap.get(propName+sql)!=null){
//				sxml.setSearchsql(sqlMap.get(key));
				JetTemplate template=sqlMap.get(propName+sql);
				UnsafeCharArrayWriter out = new UnsafeCharArrayWriter();
				template.render(model, out);
				return toSQL(out.toString(),model);
			}
			//鍒涘缓閰嶇疆瀹炰緥
//			Configuration cfg = new Configuration();
//			cfg.setDefaultEncoding("utf-8");
////			cfg.setDirectoryForTemplateLoading(new File("D:\\Workspaces\\Test\\src"));
//			StringTemplateLoader s =new StringTemplateLoader();
//			s.putTemplate("sql", sql);
//			cfg.setTemplateLoader(s);    
//			cfg.setObjectWrapper(new DefaultObjectWrapper());
//
//			//鑾峰彇妯℃澘
//			
//			Template temp = cfg.getTemplate("sql");
//			temp.setEncoding("utf-8");
//			
//			StringWriter writer = new StringWriter();    
//			temp.process(model, writer);   
//			String value=writer.toString();
			
			JetTemplate template = engine.createTemplate(sql);
			sqlMap.put(propName+sql, template);
			UnsafeCharArrayWriter out = new UnsafeCharArrayWriter();
			template.render(model, out);
			
//			sxml.setSearchsql(value);
			return toSQL(out.toString(),model);
//			 System.out.println(writer.toString());
		}catch (Exception e) {
			logger.error("", e);
		}
		return sxml;
	}
	
	/**
	 * 瑙ｆ瀽sql 涓殑鍗犱綅绗︼紝骞惰繑鍥炴煡璇㈠璞�
	 * @param sql
	 * @param par
	 * @return
	 */
	public static SqlXML toSQL(String sql,Map par){
		sql=SQLReplace(sql, par);
		SqlXML sxml=new SqlXML();
		String[] sqls = StringUtil.split(sql, "#");
		if(sqls.length<=1){
			Object[] o=new Object[]{};
			sxml.setPar(o);
			sxml.setSearchsql(sql);
		}else{
			Object[] o=new Object[sqls.length/2];
			int cnt=0;	
			int i=0;
			final StringBuffer tosqlBuffer=new StringBuffer();
			for (String s:sqls) {
				 if(cnt==0){
					 tosqlBuffer.append(s+" ");
					 cnt++;
				 }else{
					 tosqlBuffer.append("? ");
					 o[i]=par.get(s.trim());
					 i++;
					 cnt=0;
				 }
			}
			sxml.setPar(o);
			sxml.setSearchsql(tosqlBuffer.toString());
		}
		return sxml;
	}
	
	
	public static String SQLReplace(String sql,Map par){

		final StringBuffer tosqlBuffer=new StringBuffer();
		SqlXML sxml=new SqlXML();
		String[] sqls = StringUtil.split(sql, "$");
		if(sqls.length<=1){
			Object[] o=new Object[]{};
			sxml.setPar(o);
			sxml.setSearchsql(sql);
			return sql;
		}else{
			Object[] o=new Object[sqls.length/2];
			int cnt=0;	
			int i=0;
			for (String s:sqls) {
				 if(cnt==0){
					 tosqlBuffer.append(s+"");
					 cnt++;
				 }else{
					 tosqlBuffer.append(par.get(s.trim()));
					 o[i]=par.get(s.trim());
					 i++;
					 cnt=0;
				 }
			}
//			sxml.setPar(o);
//			sxml.setSearchsql(tosqlBuffer.toString());
		}
		return tosqlBuffer.toString();
	}
	public static void main(String[] args) {
		System.out.println(1%2);
		System.out.println(2%2);
		System.out.println(3%2);
		System.out.println(4%2);
		System.out.println(5%2);
		System.out.println(6%2);
	}
}
