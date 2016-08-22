

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.google.common.base.Charsets;
import com.rodcell.comm.util.FilesUtil;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年7月25日 上午11:54:17 
 * 类说明 
 */
public class Test {
	
	
	public void test(){
		 ScriptEngineManager sem = new ScriptEngineManager();   
		    /* 
		     *sem.getEngineByExtension(String extension)参数为js    
		      sem.getEngineByMimeType(String mimeType) 参数为application/javascript 或者text/javascript    
		      sem.getEngineByName(String shortName)参数为js或javascript或JavaScript  
		     */  
		    ScriptEngine se = sem.getEngineByName("js");   
		    try   
		    {   
		      List<String> ls =FilesUtil.readLines(new File("D:/js.txt"), Charsets.UTF_8 );
		      
		      
		      
		      String script ="";// FilesUtil.readLines("D:/js.txt", "utf-8");  
		      for (String s:ls) {
		    	  script+=s;
			}
		      se.eval(script);   
		      Invocable inv2 = (Invocable) se;   
		      String res1=(String)inv2.invokeFunction("UrlEncode","ใช้บัตร");   
			     
		      String res=(String)inv2.invokeFunction("UrlDecode",res1);   
		      System.out.println(res);  
		    }   
		    catch(Exception e)   
		    {   
		        e.printStackTrace();  
		    }   
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		new Test().test();
		//System.out.println(new String("ä½ å¥½".getBytes(),""));
		
		Properties pps=System.getProperties();
  		 
  		 System.out.println(pps.get("file.encoding"));
  		System.out.println(Charset.defaultCharset());
  		 
  		System.out.println(URLDecoder.decode("%E0%B8%AD%E0%B8%B1%E0%B8%81%E0%B8%A9%E0%B8%A3%E0%B9%84%E0%B8%97%E0%B8%A2","utf-8"));
	}
}
