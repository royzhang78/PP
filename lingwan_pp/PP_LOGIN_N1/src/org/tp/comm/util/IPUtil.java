package org.tp.comm.util;
 

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
 
//http://tool.17mon.cn/ipdb.html
public class IPUtil {
//	private String IP_DATA_PATH = "E:\\workspace\\TemplateServer\\config\\ipdb.dat";
	private DataInputStream inputStream = null;
	private long fileLength = -1;
	private int dataLength = -1;
	private Map<String, String> cacheMap = null;
	private byte[] allData = null;
	private static IPUtil iputil;

	public IPUtil() {
		
//		String path = IPUtil.class.getProtectionDomain().
//				getCodeSource().getLocation().getFile();
//		if(path.startsWith("/"))
//			path = path.substring(1,
//					path.length());
	
		String path = IPUtil.class.getProtectionDomain().
				getCodeSource().getLocation().getFile();
		if(path.startsWith("/"))
			path = "/"+path;
		
		File file = new File(path+"\\ipdb.dat");
		try {
			inputStream = new DataInputStream(new FileInputStream(file));
			fileLength = file.length();
			cacheMap = new HashMap<String, String>();
			if (fileLength > Integer.MAX_VALUE) {
				throw new Exception("the filelength over 2GB");
			}

			dataLength = (int) fileLength;
			allData = new byte[dataLength];
			inputStream.read(allData, 0, dataLength);
			dataLength = (int) getbytesTolong(allData, 0, 4,
					ByteOrder.BIG_ENDIAN);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private long getbytesTolong(byte[] bytes, int offerSet, int size,
			ByteOrder byteOrder) {
		if ((offerSet + size) > bytes.length || size <= 0) {
			return -1;
		}
		byte[] b = new byte[size];
		for (int i = 0; i < b.length; i++) {
			b[i] = bytes[offerSet + i];
		}

		ByteBuffer byteBuffer = ByteBuffer.wrap(b);
		byteBuffer.order(byteOrder);

		long temp = -1;
		if (byteBuffer.hasRemaining()) {
			temp = byteBuffer.getInt();
		}
		return temp;
	}

	private long ip2long(String ip) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(ip);
		byte[] bytes = address.getAddress();
		long reslut = getbytesTolong(bytes, 0, 4, ByteOrder.BIG_ENDIAN);
		return reslut;
	}

	private int getIntByBytes(byte[] b, int offSet) {
		if (b == null || (b.length < (offSet + 3))) {
			return -1;
		}

		byte[] bytes = Arrays.copyOfRange(allData, offSet, offSet + 3);
		byte[] bs = new byte[4];
		bs[3] = 0;
		for (int i = 0; i < 3; i++) {
			bs[i] = bytes[i];
		}

		return (int) getbytesTolong(bs, 0, 4, ByteOrder.LITTLE_ENDIAN);
	}

	public String findGeography(String address) {
		if (address == null) {
			return "illegal address";
		}

		if (dataLength < 4 || allData == null) {
			return "illegal ip data";
		}

		String ip = "127.0.0.1";
		try {
			ip = Inet4Address.getByName(address).getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// String[] ipArray = StringUtils.split(ip, ".");
		// String[] ipArray = ip.split(".");

		// String[] ipArray = {"8", "8", "8", "8"};
		String[] ipArray = ip.split("\\.");
		int ipHeadValue = Integer.parseInt(ipArray[0]);
		if (ipArray.length != 4 || ipHeadValue < 0 || ipHeadValue > 255) {
			return "illegal ip";
		}

		if (cacheMap.containsKey(ip)) {
			return cacheMap.get(ip);
		}

		long numIp = 1;
		try {
			numIp = ip2long(address);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		int tempOffSet = ipHeadValue * 4 + 4;
		long start = getbytesTolong(allData, tempOffSet, 4,
				ByteOrder.LITTLE_ENDIAN);
		int max_len = dataLength - 1028;
		long resultOffSet = 0;
		int resultSize = 0;

		for (start = start * 8 + 1024; start < max_len; start += 8) {
			if (getbytesTolong(allData, (int) start + 4, 4,
					ByteOrder.BIG_ENDIAN) >= numIp) {
				resultOffSet = getIntByBytes(allData, (int) (start + 4 + 4));
				resultSize = (char) allData[(int) start + 7 + 4];
				break;
			}
		}

		if (resultOffSet <= 0) {
			return "resultOffSet too small";
		}

		byte[] add = Arrays.copyOfRange(allData, (int) (dataLength
				+ resultOffSet - 1024),
				(int) (dataLength + resultOffSet - 1024 + resultSize));
		try {
			if (add == null) {
				cacheMap.put(ip, new String("no data found!!"));
			} else {
				cacheMap.put(ip, new String(add, "UTF-8"));
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return cacheMap.get(ip);
	}
	
	public static String findip(String ip){
		if(IPUtil.iputil==null)
			IPUtil.iputil = new IPUtil();
		return IPUtil.iputil.findGeography(ip); 
	}

	
	
	public static String getIpAddr(HttpServletRequest request) {
		if (request != null) {
			try {
				String ip = request.getHeader("x-forwarded-for");
				
				if (ip == null || ip.isEmpty() || "X-Real-IP".equalsIgnoreCase(ip)) {
					ip = request.getHeader("X-Real-IP");
//					logger.debug("getRemoteAddr:" + ip);
				}
//				logger.debug("x-forwarded-for:" + ip);
				if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("Proxy-Client-IP");
//					logger.debug("Proxy-Client-IP:" + ip);
				}
				if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
//					logger.debug("WL-Proxy-Client-IP:" + ip);
				}
				if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
//					logger.debug("getRemoteAddr:" + ip);
				}
				
				
				return ip;
			} catch (Exception e) {
//				logger.warn(e, e);
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) { 
		long s=System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
//			IPUtil o =new IPUtil();
//			o.findGeography("61.170.217.193");
			IPUtil.findip("120.199.11.146");
		}
		System.out.println((System.currentTimeMillis()-s)+IPUtil.findip("120.199.11.146"));
	}
}
