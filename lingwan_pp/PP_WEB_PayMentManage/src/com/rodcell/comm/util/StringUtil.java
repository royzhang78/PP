package com.rodcell.comm.util;

import java.text.NumberFormat;
import java.util.Iterator;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class StringUtil {
	public static void main(String[] args) {
		System.out.println(StringUtil.split("1,2,3,4,5,6,7,8,9,0",","));
		long s = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			String a[]="1,2,3,4,5,6,7,8,9,0".split(",");
//			String a[]=StringUtil.split("1,2,3,4,5,6,7,8,9,0",",");
		}
		
		long end = System.currentTimeMillis();
		System.out.println(end-s);
//		System.out.println(repeat("123456",2));
//		System.out.println(isNullOrEmpty(null));
//		System.out.println(isNullOrEmpty(" 1"));
//		System.out.println(joinSomeStrings(new String[]{"1","2","3"},","));
//		String str= "1  |  2 | 3";
//		String []s =StringUtil.split(str,"|");
//		System.out.println(s.length);
////		String []s=str.split( "\\| ");
//		for (int i = 0; i < s.length; i++) {
//			System.out.println(s[i]);
//		}
	}

	public static String padrigth(int n){
		 
//		NumberFormat formatter = NumberFormat.getNumberInstance();
//		formatter.setMinimumIntegerDigits(4);
//		formatter.setGroupingUsed(false);
//		String s = formatter.format(n);
		
		int strlen = (n+"").length();
		StringBuffer s=new StringBuffer();
		s.append(n+"");
		int len=4;
		if(strlen<4)
		for (int i = strlen; i < len; i++) {
			s.insert(0, "0");
		}
		return s.toString();
	}
	/**
	 * 字符串split 
	 * @param str
	 * @param split 无需转义
	 * @return
	 */
	public static String[] split(String str,String split){		
		Iterator <String>iterator = Splitter.on(split).split(str).iterator(); 
		return Lists.newArrayList(iterator).toArray(new String[0]);
	}
	
	
	public static String toString(String txt){
		return txt==null?"":txt.trim();
	}
	/**
	 * split反向操作
	 * @param str 
	 * @param separator 
	 * @return
	 */
	public static String joinSomeStrings(String []str,String separator){
		
		ImmutableSet<String> strings = ImmutableSet.copyOf(str);
		return Joiner.on(separator).join(strings);
	}
	
	
	/**
	 * 判断字符串是否为空或者为null
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str){		
		return Strings.isNullOrEmpty(str);
	}
	
	
	/**
	 * 将字符串循环合并 如str为123，count为2，则返回123123
	 * @param str
	 * @param count
	 * @return
	 */
	public static String repeat(String str,int count){
		return  Strings.repeat(str, count);
	}
	
	
	/**
	 * String替换
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceAll(String s,String regex,String replacement){
		return s.replaceAll(regex, replacement);
	}
	
	

	/**

     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * 
     * @param value
     *            指定的字符串
     * @return 字符串的长度
     */
    public static int stringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    
    /** *//*****************************************************
    * 功能介绍:将unicode字符串转为汉字
    * 输入参数:源unicode字符串
    * 输出参数:转换后的字符串
    *****************************************************/
	public static  String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}
	
	
	
	 public static String byteToString(byte b) {
		  byte high, low;
		  byte maskHigh = (byte) 0xf0;
		  byte maskLow = 0x0f;
		  high = (byte) ((b & maskHigh) >> 4);
		  low = (byte) (b & maskLow);
		  StringBuffer buf = new StringBuffer();
		  buf.append(findHex(high));
		  buf.append(findHex(low));
		  return buf.toString();
		 }
	 
	 private static char findHex(byte b) {
		 int t = new Byte(b).intValue();
		 t = t < 0 ? t + 16 : t;
		 if ((0 <= t) &&(t <= 9)) {
		 return (char)(t + '0');
		 }
		 return (char)(t-10+'A');
		 }
	 
	 
	 
	 public static byte[] HexString2Bytes(String src) {
		  int len = src.length();
		  byte[] ret = new byte[len / 2];
		  byte[] tmp = src.getBytes();
		  for (int i = 0; i < len; i += 2) {
		   ret[i / 2] = uniteBytes(tmp[i], tmp[i + 1]);
		  }
		  return ret;
		 }
	 
	 
	 private static byte uniteBytes(byte src0, byte src1) {
		  byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
		    .byteValue();
		  _b0 = (byte) (_b0 << 4);
		  byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
		    .byteValue();
		  byte ret = (byte) (_b0 ^ _b1);
		  return ret;
		 }
}
