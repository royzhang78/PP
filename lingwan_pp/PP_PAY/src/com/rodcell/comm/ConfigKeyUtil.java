package com.rodcell.comm;

import java.util.HashMap;
import java.util.Map;

import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.sys.SysPayStaticConfigDao;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月6日 上午11:17:48 
 * 类说明 
 */
public class ConfigKeyUtil {
	
	
	public static String KeyReplace(String txt,Map par){
		String s = requestKeyReplace(txt, par,"$");
		s=staticKeyReplace(s, "#");
		return s;
	}
	
	public static String requestKeyReplace(String txt,Map par,String tpl){
		final StringBuffer tosqlBuffer=new StringBuffer();		
		String[] sqls = StringUtil.split(txt, tpl);
		if(sqls.length<=1){			
			return txt;
		}else{
			Object[] o=new Object[sqls.length/2];
			int cnt=0;	
			int i=0;
			for (String s:sqls) {
				 if(cnt==0){
					 tosqlBuffer.append(s+"");
					 cnt++;
				 }else{
					 String tmp=(String)par.get(s.trim());
					 tosqlBuffer.append(tmp);
					 o[i]=tmp;
					 i++;
					 cnt=0;
				 }
			}
		}
		return tosqlBuffer.toString();
	}
	
	
	public static String staticKeyReplace(String txt,String tpl){
		SysPayStaticConfigDao sysPayStaticConfigDao=(SysPayStaticConfigDao) Constant.CTX.getBean("sysPayStaticConfigDao");
		final StringBuffer tosqlBuffer=new StringBuffer();		
		String[] sqls = StringUtil.split(txt, tpl);
		if(sqls.length<=1){			
			return txt;
		}else{
			Object[] o=new Object[sqls.length/2];
			int cnt=0;	
			int i=0;
			for (String s:sqls) {
				 if(cnt==0){
					 tosqlBuffer.append(s+"");
					 cnt++;
				 }else{
					 String tmp = sysPayStaticConfigDao.findPayStaticConfigbyKey(s.trim());
					 tosqlBuffer.append(tmp);
					 o[i]=tmp;
					 i++;
					 cnt=0;
				 }
			}
		}
		return tosqlBuffer.toString();
	}
	
	
	public static void main(String[] args) {
		String s ="$a$#23#$b$$c$";
		Map m = new HashMap();
		m.put("a", "1");
		m.put("b", "2");
		System.out.println(KeyReplace(s, m));
	}

}
