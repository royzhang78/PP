package com.rodcell.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rodcell.comm.util.JSONUtil;


public class AppleItunesUtil {
	public static Logger logger = Logger.getLogger(AppleItunesUtil.class);
	
	public static String Connection(String urlstr,Map<String,String> parameters,String parameter) throws Exception {
		URL url = new URL(urlstr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true); 
		conn.setRequestMethod("POST"); 
		String para=""; 
		if(parameters!=null){
			Iterator<String> iterator=parameters.keySet().iterator();
			while(iterator.hasNext()){
				String key=iterator.next();
				if(!"".equals(para)){
					para+="&";
				}
				para+=key+"="+parameters.get(key);
			}
		}else{
			para=parameter;
		}
		logger.info("url:"+urlstr+"  parameter:"+para);
		conn.getOutputStream().write(para.getBytes()); 
		conn.getOutputStream().flush(); 
		conn.getOutputStream().close(); 
		try {
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				 return null;
			}
		} catch (IOException e) {
			logger.error("WebConnectionUtil.Connection error",e);
			return null;
		}

		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (conn != null) {
			conn.disconnect();
		}
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		AppleItunesUtil c=new AppleItunesUtil();
		Map<String,String> parameters=new HashMap();
		parameters.put("userid", "test");
		Map p = new HashMap();
		
		p.put("receipt-data", "ewoJInNpZ25hdHVyZSIgPSAiQWdxek5IUTExL2ljYUdaa1E2UllIQnhpU2JpdmNsSVVpMnRGSEJiSEtFTzJzK1duZ1IzTzlxMzdxK3UvQms4SFFLQ0JwRGI0NDd3T0JXSEE2a1BTbnFramk0dW1XMkdsVzI2QWZoQmNwY0FOSFNXbmcyNWI0eUtNYTBlZS91WWt5SGl1R0NDTjJkZjRWU2NFMU9XMjBMRFZxazVYa09KOG9KMmNGMWZoSUxseUFBQURWekNDQTFNd2dnSTdvQU1DQVFJQ0NHVVVrVTNaV0FTMU1BMEdDU3FHU0liM0RRRUJCUVVBTUg4eEN6QUpCZ05WQkFZVEFsVlRNUk13RVFZRFZRUUtEQXBCY0hCc1pTQkpibU11TVNZd0pBWURWUVFMREIxQmNIQnNaU0JEWlhKMGFXWnBZMkYwYVc5dUlFRjFkR2h2Y21sMGVURXpNREVHQTFVRUF3d3FRWEJ3YkdVZ2FWUjFibVZ6SUZOMGIzSmxJRU5sY25ScFptbGpZWFJwYjI0Z1FYVjBhRzl5YVhSNU1CNFhEVEE1TURZeE5USXlNRFUxTmxvWERURTBNRFl4TkRJeU1EVTFObG93WkRFak1DRUdBMVVFQXd3YVVIVnlZMmhoYzJWU1pXTmxhWEIwUTJWeWRHbG1hV05oZEdVeEd6QVpCZ05WQkFzTUVrRndjR3hsSUdsVWRXNWxjeUJUZEc5eVpURVRNQkVHQTFVRUNnd0tRWEJ3YkdVZ1NXNWpMakVMTUFrR0ExVUVCaE1DVlZNd2daOHdEUVlKS29aSWh2Y05BUUVCQlFBRGdZMEFNSUdKQW9HQkFNclJqRjJjdDRJclNkaVRDaGFJMGc4cHd2L2NtSHM4cC9Sd1YvcnQvOTFYS1ZoTmw0WElCaW1LalFRTmZnSHNEczZ5anUrK0RyS0pFN3VLc3BoTWRkS1lmRkU1ckdYc0FkQkVqQndSSXhleFRldngzSExFRkdBdDFtb0t4NTA5ZGh4dGlJZERnSnYyWWFWczQ5QjB1SnZOZHk2U01xTk5MSHNETHpEUzlvWkhBZ01CQUFHamNqQndNQXdHQTFVZEV3RUIvd1FDTUFBd0h3WURWUjBqQkJnd0ZvQVVOaDNvNHAyQzBnRVl0VEpyRHRkREM1RllRem93RGdZRFZSMFBBUUgvQkFRREFnZUFNQjBHQTFVZERnUVdCQlNwZzRQeUdVakZQaEpYQ0JUTXphTittVjhrOVRBUUJnb3Foa2lHOTJOa0JnVUJCQUlGQURBTkJna3Foa2lHOXcwQkFRVUZBQU9DQVFFQUVhU2JQanRtTjRDL0lCM1FFcEszMlJ4YWNDRFhkVlhBZVZSZVM1RmFaeGMrdDg4cFFQOTNCaUF4dmRXLzNlVFNNR1k1RmJlQVlMM2V0cVA1Z204d3JGb2pYMGlreVZSU3RRKy9BUTBLRWp0cUIwN2tMczlRVWU4Y3pSOFVHZmRNMUV1bVYvVWd2RGQ0TndOWXhMUU1nNFdUUWZna1FRVnk4R1had1ZIZ2JFL1VDNlk3MDUzcEdYQms1MU5QTTN3b3hoZDNnU1JMdlhqK2xvSHNTdGNURXFlOXBCRHBtRzUrc2s0dHcrR0szR01lRU41LytlMVFUOW5wL0tsMW5qK2FCdzdDMHhzeTBiRm5hQWQxY1NTNnhkb3J5L0NVdk02Z3RLc21uT09kcVRlc2JwMGJzOHNuNldxczBDOWRnY3hSSHVPTVoydG04bnBMVW03YXJnT1N6UT09IjsKCSJwdXJjaGFzZS1pbmZvIiA9ICJld29KSW05eWFXZHBibUZzTFhCMWNtTm9ZWE5sTFdSaGRHVXRjSE4wSWlBOUlDSXlNREV5TFRBeExUQTFJREl3T2pJeE9qQTRJRUZ0WlhKcFkyRXZURzl6WDBGdVoyVnNaWE1pT3dvSkltOXlhV2RwYm1Gc0xYUnlZVzV6WVdOMGFXOXVMV2xrSWlBOUlDSXhNREF3TURBd01ERTVPREV3TnpFeUlqc0tDU0ppZG5KeklpQTlJQ0l3TGpBdU1DSTdDZ2tpZEhKaGJuTmhZM1JwYjI0dGFXUWlJRDBnSWpFd01EQXdNREF3TVRrNE1UQTNNVElpT3dvSkluRjFZVzUwYVhSNUlpQTlJQ0l4SWpzS0NTSnZjbWxuYVc1aGJDMXdkWEpqYUdGelpTMWtZWFJsTFcxeklpQTlJQ0l4TXpJMU9ESXpOalk0TURBd0lqc0tDU0p3Y205a2RXTjBMV2xrSWlBOUlDSmpiMjB1YVdaeVpXVXViWGwwYjNkdUxqVXdZU0k3Q2draWFYUmxiUzFwWkNJZ1BTQWlORGt6TWpFeU1URTVJanNLQ1NKaWFXUWlJRDBnSW1OdmJTNXBabkpsWlM1dGVYUnZkMjRpT3dvSkluQjFjbU5vWVhObExXUmhkR1V0YlhNaUlEMGdJakV6TWpVNE1qTTJOamd3TURBaU93b0pJbkIxY21Ob1lYTmxMV1JoZEdVaUlEMGdJakl3TVRJdE1ERXRNRFlnTURRNk1qRTZNRGdnUlhSakwwZE5WQ0k3Q2draWNIVnlZMmhoYzJVdFpHRjBaUzF3YzNRaUlEMGdJakl3TVRJdE1ERXRNRFVnTWpBNk1qRTZNRGdnUVcxbGNtbGpZUzlNYjNOZlFXNW5aV3hsY3lJN0Nna2liM0pwWjJsdVlXd3RjSFZ5WTJoaGMyVXRaR0YwWlNJZ1BTQWlNakF4TWkwd01TMHdOaUF3TkRveU1Ub3dPQ0JGZEdNdlIwMVVJanNLZlE9PSI7CgkiZW52aXJvbm1lbnQiID0gIlNhbmRib3giOwoJInBvZCIgPSAiMTAwIjsKCSJzaWduaW5nLXN0YXR1cyIgPSAiMCI7Cn0=");
//		String par=p.toString();
		String par=JSONUtil.objectToString(p);
		parameters.put("id", "test");
		//https://sandbox.itunes.apple.com/verifyReceipt
		//https://buy.itunes.apple.com/verifyReceipt
		long ss=System.currentTimeMillis();
		String rs=c.Connection("https://sandbox.itunes.apple.com/verifyReceipt",null,par);
//		JSONObject o=JSONObject.fromObject(rs);
//		int status=Integer.parseInt(String.valueOf(o.get("status")));
		
		System.out.println(rs.contains("quantity"));
		System.out.println(System.currentTimeMillis()-ss);
		System.out.println(rs);
	}

}
