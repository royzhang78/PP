package com.rodcell.comm.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年4月29日 下午3:25:52 
 * 类说明 
 */
public class FilesUtil {
	public static void main(String[] args) throws IOException {
		
		File f = new File("E:/temp/1.txt");
		List<String> sl =Files.readLines(f, Charset.defaultCharset());
		String s ="";
		for (String s2:sl) {
			s+=s2;
		}
//		String s ="{    \"callPara\": {        \"gameRoleId\": \"1000168312\",        \"releaseChannel\": \"\",        \"locale\": \"EN\",        \"userName\": \"b81fd602c5401eb7\",        \"signedData\": {            \"orderId\": \"12999763169054705758.1346717257536288\",            \"packageName\": \"com.guohe.glinkthaigoo\",            \"productId\": \"iab.product.49\",            \"purchaseTime\": 1403770197643,            \"purchaseState\": 0,            \"purchaseToken\": \"kbddhobjpigimofjhhamicge.AO-J1Ozn1243z-MG4xhal3_2dl5y3eU3W6vo2HX2iLBPf9aUXecP0Zg7VEP55jrPSVaU6K5qkWG908j_9TNY_HhPl49A40yJbqCRLmH7TFMopIgsc7lwSFmliKErBF_kyXWRlaJw_8RV\"        },        \"cparam\": \"15903\",        \"clientIp\": \"110.168.238.191\",        \"udid\": \"b81fd602c5401eb7\",        \"gameName\": \"ALICE-ANDROID-TH\",        \"signature\": \"PhtxDIgys6fwJOBrXMYhjLZqKPed6CJLRVJYxI+5r6DmBfA/6d/wHgP6+J95P1B5DRfR4zA95UcFeNe2IuIAeCMGnouWZImhg4ao+GK4b81IcBFwQDdbuZJikROseyc3ksNvW2Z9UHlD/UQi5zlfr0/1QD+QcMXJ6wTxGnGcp6CSdCP6+xzqIQl1Xx1NDJpbz6PsT5Yj0DEH7K+Uw8umn/slx2mZpTrpXfHXhrlNiYokwM1UH1tcNh9+GaXgZwcQhCPLlmtKTBi6dIO5+pfBN8hfFjzr/omUvIOuJh29BLaSV7Tr9sJbv7pPajISECpTBC9090EXQKI/2IQyuJeZ1Q==\",        \"clientType\": \"android\",        \"productId\": \"iab.product.49\"    },    \"serviceName\": \"InGooglePayService\"}CMGnouWZImhg4ao+GK4b81IcBFwQDdbuZJikROseyc3ksNvW2Z9UHlD/UQi5zlfr0/1QD+QcMXJ6wTxGnGcp6CSdCP6+xzqIQl1Xx1NDJpbz6PsT5Yj0DEH7K+Uw8umn/slx2mZpTrpXfHXhrlNiYokwM1UH1tcNh9+GaXgZwcQhCPLlmtKTBi6dIO5+pfBN8hfFjzr/omUvIOuJh29BLaSV7Tr9sJbv7pPajISECpTBC9090EXQKI/2IQyuJeZ1Q==',        'clientType': 'android'    }}";
		JSONObject json = JSONObject.parseObject(s);
		System.out.println(JSONUtil.JsonToObject(json.get("callPara").toString(), Map.class));//cparam callPara
		
//		Files.readLines(file, Charset.defaultCharset());
		
	}
	 
	public static void createParentDirs(String path) throws IOException{
		Files.createParentDirs(new File(path));
	}
	
	public static void copy(File from,File to) throws IOException{
		Files.copy(from, to);
	}
	
	/**
	 * File file,Charsets.UTF_8
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(File file,Charset charset) throws IOException{
		return Files.readLines(file, charset);//Charset.defaultCharset().forName(charset)
	}
	
	
	public static void move(File from,File to) throws IOException{
		Files.move(from, to);
	}
	
	/**
	 * 
	 * @param txt
	 * @param to
	 * @param charset  Charsets.UTF_8
	 * @throws IOException
	 */
	public static void write(String txt,File to,Charset charset) throws IOException{
		CharSequence from=txt;
		Files.write(from, to, charset);		 
	}
}
