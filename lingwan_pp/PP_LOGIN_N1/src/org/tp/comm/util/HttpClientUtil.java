package org.tp.comm.util;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jodd.http.HttpProgressListener;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.log.Logger;
import jodd.log.LoggerFactory;
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpVersion;
////import org.apache.http.NameValuePair;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.conn.ssl.X509HostnameVerifier;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.params.HttpProtocolParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;


public class HttpClientUtil {
	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HttpClientUtil.class);

	
	public static void postFile(String url,File file){
		   HttpResponse response = HttpRequest
        .post(url)
        .form("file", file)
        .monitor(new HttpProgressListener() {
            
			@Override
			public void transferred(int len) {
				logger.info(""+len/size);
			}
        })
        .send();
	}
	public static String get(String url,boolean isGzip){
		HttpRequest q =HttpRequest
		        .get(url);
		 if(isGzip){
	        	q.acceptEncoding("gzip");
	        }
		HttpResponse response =q.send();
		String s = response.bodyText();
		response.close();
		return s;
	}
	
	public static String post(String url,Map par,boolean isGzip){
		HttpRequest q =HttpRequest
		        .post(url).query(par);
		        if(isGzip){
		        	q.acceptEncoding("gzip");
		        }
		HttpResponse response =  q.send();
		String s = response.bodyText();
		
		response.close();
		return s;
//		HttpResponse response = HttpRequest
//		        .get("http://www.liferay.com")
//		        .acceptEncoding("gzip")
//		        .send();
//		    System.out.println(response.unzip());
	}
	
	public static void main(String[] args) {
//		Map data = new HashMap();
//		data.put("a", "让我");
//		System.out.println(HttpClientUtil.post("http://127.0.0.1:8080/foo", data,false));
		String json="{\"callPara\":{\"login_type\":\"2\",\"login_name\":\"868192021251349\",\"u_deviceid\":\"868192021251349\",\"u_releaseChannel\":\"GooglePlay\",\"gameId\":\"GravityAttract-ANDROID-EN_D\",\"locale\":\"\",\"clientType\":\"\",\"gpsad_id\":\"60dc8407-6529-48d7-a180-1d58c781d4fb\",\"android_id\":\"f6a71d6434e042ac\"},\"serviceName\":\"startGameJson\"}";
//		Map p = new HashMap();
//		p.put("q", "ee");
//		p.put("2", "e2e");
//		HttpRequest q =HttpRequest.post("http://127.0.0.1:8082/api").bodyText(json);
		HttpRequest q =HttpRequest.post("http://dwauc.fireflygames.com/pplogin/api").bodyText(json);
		HttpResponse response =  q.send();
		String s = response.bodyText();
		
		response.close();
		System.out.println(s);
		
		
		
//		json="{\"callPara\":{\"login_type\":\"2\",\"login_name\":\"868192021251349\",\"u_deviceid\":\"868192021251349\",\"u_releaseChannel\":\"GooglePlay\",\"gameId\":\"GravityAttract-ANDROID-EN_D\",\"locale\":\"\",\"clientType\":\"\",\"gpsad_id\":\"60dc8407-6529-48d7-a180-1d58c781d4fb\",\"android_id\":\"f6a71d6434e042ac\"},\"serviceName\":\"startGameJson\"}";
////		Map p = new HashMap();
////		p.put("q", "ee");
////		p.put("2", "e2e");
////		HttpRequest q =HttpRequest.post("http://127.0.0.1:8082/api").bodyText(json);
//		 q =HttpRequest.post("http://dwauc.fireflygames.com/pplogin/api").bodyText(json);
//		 response =  q.send();
//		 s = response.bodyText();
//		
//		response.close();
//		System.out.println(s);
		
		
//		System.out.println(HttpClientUtil.post("http://jodd.org/doc/http.html", data));
//		文件上传并监控
//		   HttpResponse response = HttpRequest
//			        .post("http://localhost:8081/hello")
//			        .form("file", file)
//			        .monitor(new HttpProgressListener() {
//			            @Override
//			            public void transferred(long len) {
//			                System.out.println(len/size);
//			            }
//			        })
//			        .send();
	}
	
	
	

}
