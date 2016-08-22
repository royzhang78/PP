package com.rodcell.comm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class MD5Util {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
				file.length());
		messagedigest.update(byteBuffer);
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	
	
	public static  boolean checkArgs(Map<String,String> par,String partnerPassword,String PasswordHash){
		boolean b =false;
		Map<String, String> parSource = new HashMap();
		try {
			BeanUtils.copyProperties(parSource, par);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer bf=new StringBuffer();
		
		List<String> keys = new ArrayList<String>(par.keySet());
		// sort is requiered by mol
		Collections.sort(keys);
		String prestr = "";
		int i = 0;
		for (String key : keys) {
			String value = (String) par.get(key);
			if (value == null || value.trim().isEmpty()) {
				continue;
			}
			prestr += key+"="+value+"&";
//			if(i>keys.size())prestr+="&";
			i++;
		}
		prestr=prestr.substring(0, prestr.length()-1);
		  

		bf.append("CountryId="+toString(par.get("CountryId"))+"&");
		bf.append("CountryISO="+toString(par.get("CountryISO"))+"&");
		bf.append("OpId="+toString(par.get("OpId"))+"&");
		bf.append("Origin="+toString(par.get("Origin"))+"&");
		bf.append("PricePointId="+toString(par.get("PricePointId"))+"&");
		bf.append("ProductId="+toString(par.get("ProductId"))+"&");
		bf.append("Test="+toString(par.get("Test"))+"&");
		bf.append("Text="+toString(par.get("Text"))+"&");
		bf.append("TxId="+toString(par.get("TxId")));
		
		
		if(partnerPassword==null)partnerPassword="";
		String partner=partnerPassword+bf.toString();
		
		String md5= DigestUtils.md5Hex(partner);
		
		b = md5.toLowerCase().equals(PasswordHash.toLowerCase());
		return b;
	}
	
	public static String toString(Object o){
		if(o==null){
			return "";
		}else{
			return String.valueOf(o).trim();
		}
	}
	
	public static void main(String[] args) throws IOException {
		 
		long begin = System.currentTimeMillis();

//		File big = new File("D:\\temp\\jre-7u11-linux-i586.tar.gz");
//		String md5 = getFileMD5String(big);
//
//		long end = System.currentTimeMillis();
//		System.out.println("md5:" + md5);
//		System.out.println("time:" + ((end - begin) / 1000) + "s");
		Map par= new HashMap();
		par.put("CountryId", "60");
		par.put("CountryISO", "MY");
		par.put("ProductId", "2142");
		par.put("PricePointId", "342");
		par.put("OpId", "187");
		par.put("Origin", "60123499997");
		par.put("Text", "MCN+CONE+TS+7c2adba4-5e73-496a-a250-ea025fa2eb14");
		par.put("TxId", "4139081265");
		par.put("Test", "0");
		
//		par.put("PasswordHash", "B43A522097508DEDB70E772AFEACCE27");
		checkArgs(par, "45f78f52", "97519C7521D35F25A9329BDCBF6072A5");
		System.out.println(DigestUtils.md5Hex("45f78f52CountryId=60&CountryISO=MY&OpId=187&Origin=60123499997&PricePointId=342&ProductId=2142&Test=0&Text=MCN CONE TS 7c2adba4-5e73-496a-a250-ea025fa2eb14&TxId=4139081265"));
		System.out.println(getMD5String("10RhUUiXzP7mO2040plTsXlCZPC6eEOm6b1MYR9713testalipay1b97f9196-3888-4a68-8634-d904499a7acchttp://202.181.234.218/pp/molSuccess.do?payId=b97f9196-3888-4a68-8634-d904499a7accv1oHrrFuem7Jlzx4xMIqPuIUwmU6BWlJ60"));

	}
}
