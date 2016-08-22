package com.rodcell.comm.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.NameValuePair;



public class HttpClientUtil {
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
//	public static String post(String url,String value,String parEncoder){
//		CloseableHttpClient client = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost(url);
//		
//		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		// 将表单的值放入postMethod中
//		httpPost.
//	}
	
	/**
	 * post方式写对象流到服务端
	 * 
	 * @param url server url
	 * @param obj Object
	 * @return OutputStream
	 */
	public static String doPostWithObject(String url, Object obj)
	{
//		String cp = new ContentProducer()
//		{
//			// 二进制流
//			public void writeTo(OutputStream outstream) throws IOException
//			{
//				ObjectOutputStream oos = new ObjectOutputStream(outstream);
////				oos.writeObject(obj);
////				oos.writeUTF(obj.toString());
//				oos.write(obj.toString().getBytes());
//				
//				oos.flush();
//				System.out.println(oos);
//				oos.close();
//			}
//		};
		String html=null;
		try {
			if(obj==null){
				obj=new Object();
			}
			logger.info("http post for url :"+url +" value:"+obj.toString());
			html = doPost(url, new StringEntity(obj.toString()));
		} catch (Exception e) {
			logger.error("",e);
		}
		logger.info("http post for url = "+url+" par:"+obj.toString()+" response="+html );
		return html;
	}
	
	
	/**
	 * 
	 * httpClient 执行post请求 返回 OutputStream对象
	 * 
	 * @param url String
	 * @param entity HttpEntity
	 * @return OutputStream
	 */
	private static String doPost(String url, HttpEntity entity)
	{
		 
		CloseableHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		
		OutputStream os = null;
		post.setEntity(entity);
		String html="";
		try
		{
			
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();//设置请求和传输超时时间
			post.setConfig(requestConfig);
			HttpResponse response = client.execute(post);
			HttpEntity resEntity = response.getEntity();
			InputStream in = resEntity.getContent();
			
//			if (resEntity != null)
//			{
//				os = new ByteArrayOutputStream();
//				int temp = 0;
//				while ((temp = in.read()) != -1)
//				{
//					os.write(temp);
//				}
				
				BufferedReader in1 = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent(), HTTP.DEF_CONTENT_CHARSET));

				  String line = null;

				 while ((line = in1.readLine()) != null) {
//		                System.out.println(line);
					 html+=line;
		         } 
				 
//				os.flush();
//				os.close();
				EntityUtils.consume(resEntity);

				return html;
				
//			}
		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally {

			
			if(client!=null ){
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
 


	
	public static String post(String url, Map<String, String> par,
			String parEncoder,Map<String,String> headerMap)  {
		 
		CloseableHttpClient client = HttpClients.createDefault();
		 
		StringBuffer sb = new StringBuffer();
		Iterator it = par.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			String value = par.get(key);
			sb.append(key+"="+value+"&");
		}
		
		String html="";
		boolean b = false;
		try {
			// int salt=RandomUtil.Random(100000000, 999999999);
			// HttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
//			httpPost.addHeader("Accept-Charset", "utf-8");  
//			httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");  
//			httpPost.addHeader("Accept-Language", "en-US,en,zh-CN,zh;q=0.8");  
//			httpPost.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22"); 
//			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//			httpPost.setHeader("Cache-Control", "max-age=0");
//			httpPost.setHeader("Connection", "keep-alive");
//			httpPost.setHeader("Cookie", "BAIDUID=360CAD0646402336EF4C73BED9BB1CB4:FG=1; bdshare_firstime=1389778197669; BD_CK_SAM=1; H_PS_PSSID=4683_5095_5229_1442_5187_5224_519");
			if(headerMap!=null){
				Set<String> set =headerMap.keySet();
				for(String key :set){
					httpPost.setHeader(key, headerMap.get(key));
				}
			}
			
			if (par != null) {

				List nvps = new ArrayList<NameValuePair>();
				Iterator<String> key = par.keySet().iterator();
				int i = 0;
				while (key.hasNext()) {
					String k = key.next();
					String value = par.get(k);
					if (parEncoder != null && !"".equals(parEncoder)) {
						value = new String(value.getBytes("UTF-8"), parEncoder);
						nvps.add(new BasicNameValuePair(k, value));
					}
					
					httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				}

			}

			CloseableHttpResponse response = client.execute(httpPost);
			try{
				if (response.getStatusLine().getStatusCode() == 302) {
				    Header[] header = response.getHeaders("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
				    String newuri = header[0].getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
				    newuri="http://"+httpPost.getURI().getHost()+"/"+newuri;
//				    System.out.println(newuri); 
				   return post(newuri, par,parEncoder,headerMap);
				 //  System.out.println(newuri); 
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					b = true;
				}
//				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), HTTP.DEF_CONTENT_CHARSET));
				BufferedReader in = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent(), HTTP.DEF_CONTENT_CHARSET));

				  String line = null;

				 while ((line = in.readLine()) != null) {
//		                System.out.println(line);
					 html+=line;
		            }
                EntityUtils.consume(response.getEntity());

//			httpPost.releaseConnection();
			}catch(Exception e ){
//				response.close();
			}finally {
				response.close();
//				client.close();
			}
			return html;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			logger.info("http post url = "+url+" par:"+sb.toString()+" response="+html );
			if(client!=null ){
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	public static String post(String url, Map<String, String> par,
			String parEncoder) throws GeneralSecurityException, IOException {
		 
		CloseableHttpClient client = HttpClients.createDefault();
	
		StringBuffer sb = new StringBuffer();
		Iterator it = par.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			String value = par.get(key);
			sb.append(key+"="+value+"&");
		}
		
		String html="";
		boolean b = false;
		try {
			// int salt=RandomUtil.Random(100000000, 999999999);
			// HttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			
			httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
			httpPost.addHeader("Accept-Charset", "utf-8");  
			httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");  
			httpPost.addHeader("Accept-Language", "en-US,en,zh-CN,zh;q=0.8");  
			httpPost.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22"); 
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.setHeader("Cache-Control", "max-age=0");
			httpPost.setHeader("Connection", "keep-alive");
//			httpPost.setHeader("Cookie", "BAIDUID=360CAD0646402336EF4C73BED9BB1CB4:FG=1; bdshare_firstime=1389778197669; BD_CK_SAM=1; H_PS_PSSID=4683_5095_5229_1442_5187_5224_519");
			if (par != null) {

				List nvps = new ArrayList<NameValuePair>();
				Iterator<String> key = par.keySet().iterator();
				int i = 0;
				while (key.hasNext()) {
					String k = key.next();
					String value = par.get(k);
					if (parEncoder != null && !"".equals(parEncoder)) {
						value = new String(value.getBytes("UTF-8"), parEncoder);
						nvps.add(new BasicNameValuePair(k, value));
					}
					
					httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				}

			}

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = client.execute(httpPost);
			try{
				if (response.getStatusLine().getStatusCode() == 302) {
				    Header[] header = response.getHeaders("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
				    String newuri = header.toString(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					b = true;
				}
//				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), HTTP.DEF_CONTENT_CHARSET));
				BufferedReader in = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent(), HTTP.DEF_CONTENT_CHARSET));

				  String line = null;

				 while ((line = in.readLine()) != null) {
//		                System.out.println(line);
					 html+=line;
		            }
                EntityUtils.consume(response.getEntity());

//			httpPost.releaseConnection();
			}catch(Exception e ){
//				response.close();
			}finally {
				response.close();
//				client.close();
			}
		
			return html;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		} finally {
			logger.info("http post url = "+url+" par:"+sb.toString()+" response="+html );
			if(client!=null ){
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	public static String get(String url, 
			String parEncoder,Map<String,String> headerMap) throws GeneralSecurityException, IOException {
		 
		CloseableHttpClient	client = HttpClients.createDefault();
		 
		String html="";
		boolean b = false;
		try {
			// int salt=RandomUtil.Random(100000000, 999999999);
			// HttpClient client = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);


			httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
			httpget.addHeader("Accept-Charset", "utf-8");  
			httpget.addHeader("Accept-Encoding", "gzip,deflate,sdch");  
			httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.8");  
			httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36"); 
//			httpget.addHeader("Cookie", "_hc.v=\"\\\"3d00595c-b9f6-4bd9-8b8f-a82a84500f59.1393470770\\\"\"; is=841265310665; abtest=\"42,105\\|41,102\\|44,107\"; pgv_pvi=292114432; ua=mger186; ctu=8adff94f29a4f0bea7b3c39b4f960a2d095187bc5ca24640ddf23a2f19fe24b1; _tr.u=VBuJbIYDGpQnn46L; __zpspc=99.1.1393578807.1393578807.1%234%7C%7C%7C%7C%7C; uniquekey=2paZrbfYmrbCytFL9gACjPxq1Ajgqs5M; visitflag=0; cityid=1; citypinyin=shanghai; cityname=5LiK5rW3; download_banner=on; RecentDealGroupIds=2146418|5044863|210764; t_track=D2146418:D2146418:D2146418:D2146418:D210764:D2146418:D2146418:T:D5044863:D2146418:D2146418:D2146418; t_refer=1002; _tr.s=lz6PZxI0OWdxqPOO; ano=Pcw1XT06zwEkAAAAYmJhNTA4ZTYtOGQzOS00ZDFhLThhMzEtMTViYmZmNDA4NWU0qTzmLIMNyQ-ON4broV1vLP3L7-E1; sid=ov2hrk45rifhx0mg3pjk1t55; PHOENIX_ID=0a01042e-1449a916b92-5ef76; tc=1; s_ViewType=1; aburl=1; cy=1; cye=shanghai; ab=; __utma=1.64405830.1394088357.1394168116.1394175113.6; __utmb=1.25.10.1394175113; __utmc=1; __utmz=1.1394088357.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); JSESSIONID=E67C1D29E307FAA2D845A59EF5315B15; lb.dp=409338122.20480.0000");
//			httpget.addHeader("Cookie", "__utma=4016041.1414732941.1394197218.1394197218.1394197218.1; __utmb=4016041.1.10.1394197218; __utmc=4016041; __utmz=4016041.1394197218.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Iwf_sid=o10zC0; Iwf_oldtopics=D343533D25398851D25343461D; Iwf_visitedfid=321D52D497; smile=1D1; CNZZDATA1000040140=708300900-1394197226-http%253A%252F%252Fwww.haoyisheng.com%252F%7C1394197226");
			if(headerMap!=null){
				Set<String> set =headerMap.keySet();
				for(String key :set){
					httpget.setHeader(key, headerMap.get(key));
				}
			}
			CloseableHttpResponse response = client.execute(httpget);
			try{
				if (response.getStatusLine().getStatusCode() == 302) {
				    Header[] header = response.getHeaders("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
				    String newuri = header[0].getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
				    newuri="http://"+httpget.getURI().getHost()+"/"+newuri;
				    System.out.println(newuri); 
				   return get(newuri, parEncoder,headerMap);
				 //  System.out.println(newuri); 
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					b = true;
				}
//				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), HTTP.DEF_CONTENT_CHARSET));
				BufferedReader in = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent(), HTTP.DEF_CONTENT_CHARSET));

				  String line = null;

				 while ((line = in.readLine()) != null) {
//		                System.out.println(line);
					 html+=line;
		            }
                EntityUtils.consume(response.getEntity());
               System.out.println(response.getHeaders("Cookie").length);

//			httpPost.releaseConnection();
			}catch(Exception e ){
//				response.close();
			}finally {
				response.close();
//				client.close();
			}
			return html;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(client!=null ){
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static String get(String url, 
			String parEncoder) throws GeneralSecurityException, IOException {
		
		CloseableHttpClient	client = HttpClients.createDefault();
		
		String html="";
		boolean b = false;
		try {
			// int salt=RandomUtil.Random(100000000, 999999999);
			// HttpClient client = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);


			httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
			httpget.addHeader("Accept-Charset", "utf-8");  
			httpget.addHeader("Accept-Encoding", "gzip,deflate,sdch");  
			httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.8");  
			httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36"); 
//			httpget.addHeader("Cookie", "_hc.v=\"\\\"3d00595c-b9f6-4bd9-8b8f-a82a84500f59.1393470770\\\"\"; is=841265310665; abtest=\"42,105\\|41,102\\|44,107\"; pgv_pvi=292114432; ua=mger186; ctu=8adff94f29a4f0bea7b3c39b4f960a2d095187bc5ca24640ddf23a2f19fe24b1; _tr.u=VBuJbIYDGpQnn46L; __zpspc=99.1.1393578807.1393578807.1%234%7C%7C%7C%7C%7C; uniquekey=2paZrbfYmrbCytFL9gACjPxq1Ajgqs5M; visitflag=0; cityid=1; citypinyin=shanghai; cityname=5LiK5rW3; download_banner=on; RecentDealGroupIds=2146418|5044863|210764; t_track=D2146418:D2146418:D2146418:D2146418:D210764:D2146418:D2146418:T:D5044863:D2146418:D2146418:D2146418; t_refer=1002; _tr.s=lz6PZxI0OWdxqPOO; ano=Pcw1XT06zwEkAAAAYmJhNTA4ZTYtOGQzOS00ZDFhLThhMzEtMTViYmZmNDA4NWU0qTzmLIMNyQ-ON4broV1vLP3L7-E1; sid=ov2hrk45rifhx0mg3pjk1t55; PHOENIX_ID=0a01042e-1449a916b92-5ef76; tc=1; s_ViewType=1; aburl=1; cy=1; cye=shanghai; ab=; __utma=1.64405830.1394088357.1394168116.1394175113.6; __utmb=1.25.10.1394175113; __utmc=1; __utmz=1.1394088357.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); JSESSIONID=E67C1D29E307FAA2D845A59EF5315B15; lb.dp=409338122.20480.0000");
//			httpget.addHeader("Cookie", "__utma=4016041.1414732941.1394197218.1394197218.1394197218.1; __utmb=4016041.1.10.1394197218; __utmc=4016041; __utmz=4016041.1394197218.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Iwf_sid=o10zC0; Iwf_oldtopics=D343533D25398851D25343461D; Iwf_visitedfid=321D52D497; smile=1D1; CNZZDATA1000040140=708300900-1394197226-http%253A%252F%252Fwww.haoyisheng.com%252F%7C1394197226");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
			httpget.setConfig(requestConfig);
			CloseableHttpResponse response = client.execute(httpget);
			try{
				if (response.getStatusLine().getStatusCode() == 200) {
					b = true;
				}
//				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), HTTP.DEF_CONTENT_CHARSET));
				BufferedReader in = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent(), HTTP.DEF_CONTENT_CHARSET));

				  String line = null;

				 while ((line = in.readLine()) != null) {
//		                System.out.println(line);
					 html+=line;
		            }
                EntityUtils.consume(response.getEntity());
//               System.out.println(response.getHeaders("Cookie").length);

//			httpPost.releaseConnection();
			}catch(Exception e ){
//				response.close();
			}finally {
				response.close();
//				client.close();
			}
			return html;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(client!=null ){
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	public static void mainTEST(String[] args) throws  IOException, GeneralSecurityException {
		HttpClientUtil p =new HttpClientUtil();
		String subject="2测试okISO-8859-1";
//		p.send(subject, "2测试ok1111");
//		String url="http://www.dianping.com/ajax/json/shopremote/search?do=hsc&c=1&s=0&q=kf";
//		String url="http://www.qq.com";
//		String url="http://www.taobao.com";
//		url="http://127.0.0.1/test";

		String url="http://bbs.haoyisheng.com/viewthread.php?tid=343533&highlight=%E5%8C%BB%E7%96%97%E5%99%A8%E6%A2%B0";
		Map m = new HashMap();
		m.put("do", "hsc");
		m.put("c", "1");
		m.put("s", "0");
		m.put("q", "kf");
		m.put("parms", "{'servlet':'demoServlet','username':'abcd'}");
//		p.get(url, null);
//		String s =p.get(url,  "UTF-8");
		CloseableHttpClient client = HttpClients.createDefault();
				
		String s= p.get(url, "UTF-8");
		

//		s= p.post(url, m, "UTF-8",client);
		
		s=new String(s.getBytes("iso8859-1"), "utf-8");
		System.out.println(s);
		client.close();
//		System.out.println(s);
		
	}
	
	
	public static void main(String[] args) throws  IOException, GeneralSecurityException {
		String url="http://127.0.0.1:8080/pp/api";
		Map map =new HashMap();
		map.put("serviceName", "getPayProductBySName");//?serviceName=getPayProductBySName&callPara={'sName':'FSTEST-ANDROID-EN'}
		map.put("callPara", "{'sName':'FSTEST-ANDROID-EN'}");
		 List<String> sl = FilesUtil.readLines(new File("d:/1.txt"), Charset.defaultCharset().forName("utf-8"));
		String str=sl.get(0);
		System.out.println(new String(HttpClientUtil.doPostWithObject(url,str).getBytes("utf-8")));
//		String url="http://127.0.0.1:8080/pp/easy2PayMO.do";
//		String obj="<?xml version=\"1.0\" ?><smsPaymentStuffRequest><transactionId>2014053011111</transactionId>"
//				+ "<price>1</price><contentCode>TS ff9f6945-dd24-41f4-81bf-e72e4042432b</contentCode>"
//				+ "<mediaCode>xxxxxxxxxxxxxyyyyyyyyyyy</mediaCode><sender>"
//				+ "<msisdn>668xyyyzzzz</msisdn><operator>DTAC</operator><ntype>POSTPAID</ntype></sender></smsPaymentStuffRequest>";
////		obj=new String(obj.getBytes("UTF-8"), "UTF-8");
//		
//		
////		HttpClientUtil.doPostWithObject(url, obj);
//		
//		StringEntity myEntity = new StringEntity(obj,
//				"UTF-8");
//		System.out.println(HttpClientUtil.doPost(url, myEntity));
//		HttpClientUtil p =new HttpClientUtil();
//		String subject="2测试okISO-8859-1";
////		p.send(subject, "2测试ok1111");
////		String url="http://www.dianping.com/ajax/json/shopremote/search?do=hsc&c=1&s=0&q=kf";
////		String url="http://www.qq.com";
////		String url="http://www.taobao.com";
////		url="http://127.0.0.1/test";
//
//		String url="http://bbs.haoyisheng.com/search.php";
//		Map m = new HashMap();
//		m.put("srchtxt", "医疗器械");
//		m.put("srchtype", "title");
//		m.put("searchsubmit", "true");
//		m.put("st", "on");
//		m.put("srchfilter", "all");
//		m.put("orderby", "lastpost");
//		m.put("ascdesc", "desc");
//		m.put("srchfid[]", "all");
////		p.get(url, null);
////		String s =p.get(url,  "UTF-8");
//		Map cookie=new HashMap();
//		cookie.put("cookie", "__utma=4016041.1414732941.1394197218.1394197218.1394197218.1; __utmc=4016041; __utmz=4016041.1394197218.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Iwf_oldtopics=D25375894D97458D12707445D109481D126037D343533D25398851D25343461D; smile=1D1; Iwf_sid=kj0ltM; Iwf_auth=c326qxlZFGPPTYOEDWzKTVXuwy8zBDANnNX5t5ugw0eEa0hWNQDTkngLd65k58dhEGOXdSuzenHw3r0Q0mK%2BSVXAfb97EA; checkpm=1; CNZZDATA1000040140=708300900-1394197226-http%253A%252F%252Fwww.haoyisheng.com%252F%7C1394199095");
//		CloseableHttpClient client = HttpClients.createDefault();
//				
////		String s= p.get(url, "UTF-8",client);
//		
//
//		String s=  p.post(url, m, "UTF-8",cookie);
//		
//		s=new String(s.getBytes("iso8859-1"), "utf-8");
//		System.out.println(s);
//		client.close();
//		System.out.println(s);
		
	}

}
