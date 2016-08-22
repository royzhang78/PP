package com.rodcell.comm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

public class CheckUtil {

	/**
	 * 身份证校验位
	 */
	public static String[] CHECK_DIGIT = { "1", "0", "X", "9", "8", "7", "6",
			"5", "4", "3", "2" };
	/**
	 * 身份证加权因子
	 */
	public static int[] gene = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
			4, 2, 1 };

	
	/**
	 * 正则表达式验证
	 * @param regEx 正则表达式
	 * @param txt 验证文本
	 * @return
	 */
	public static boolean checkPattern(String regEx,String txt){
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(txt);
		return matcher.matches();
	}
	
	
	/**
	 * 判断是否为email
	 * 
	 * @param email
	 * @return 是email返回true; 不是email返回false
	 */
	public static boolean isEmail(String email) {
		if (Strings.isNullOrEmpty(email))
			return false;
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		Matcher isMail = pattern.matcher(email);
		return isMail.matches();

	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param email
	 * @return 是数字返回true; 不是数字返回false
	 */
	public static boolean isNum(String num) {
		if (Strings.isNullOrEmpty(num))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(num);
		return isNum.matches();
	}

	/**
	 * 判断是否为手机号
	 */
	public static boolean isMobile(String mo) {
		if (Strings.isNullOrEmpty(mo))
			return false;
		Pattern pattern = Pattern.compile("^1[35]\\d{9}$");
		Matcher isMobile = pattern.matcher(mo);
		return isMobile.matches();
	}

	/**
	 * 判断是否为身份证号^(\d{15}|(\d{17}[xX\d]))$
	 */
	public static boolean isIdentityCard(String card) {
		// if(card==null||card.equals(""))return false;
		// if(card.length()!=15&&card.length()!=18)return false;
		// Pattern pattern=Pattern.compile("^(\\d{15}|(\\d{17}[xX\\d]))$");
		// Matcher isIdentityCard=pattern.matcher(card);
		// return isIdentityCard.matches();
		if (Strings.isNullOrEmpty(card))
			return false;
		if (card.length() != 15 && card.length() != 18)
			return false;
		Pattern pattern = Pattern.compile("^(\\d{15}|(\\d{17}[xX\\d]))$");
		Matcher isIdentityCard = pattern.matcher(card);
		if (!isIdentityCard.matches())
			return false;
		if (card.length() == 18) {
			int yearPrefix = Integer.parseInt(card.substring(6, 8));
			if (yearPrefix < 19 || yearPrefix > 21)
				return false;// 出生日期必须大于1900年小于2100年
			int month = Integer.parseInt(card.substring(10, 12));
			if (month > 12 || month == 0)
				return false; // 验证月
			int day = Integer.parseInt(card.substring(12, 14));
			if (day > 31 || day == 0)
				return false; // 验证日
			String checkDigit = getCheckDigitFor18(card);
			if (checkDigit.equals("-1"))
				return false;
			if (checkDigit.equals(card.substring(17, 18).toUpperCase())) {
				return true;
			} else {
				return false;
			}
		} else if (card.length() == 15) {
			int month = Integer.parseInt(card.substring(8, 10));
			if (month > 12 || month == 0)
				return false; // 验证月
			int day = Integer.parseInt(card.substring(10, 12));
			if (day > 31 || day == 0)
				return false;
			return true;
		}
		return false;
	}

	private static String getCheckDigitFor18(String card) {
		if (Strings.isNullOrEmpty(card))
			return "-1";
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum += Integer.parseInt(card.substring(i, i + 1)) * gene[i];
		}
		return CHECK_DIGIT[sum % 11];
	}

	/**
	 * 判断电话号码是否为国内手机号码<br>
	 * 系统判断客户是国内手机用户（主叫号码前缀为13/15/18的11位有效数字）<br>
	 * 
	 * @param phoneNo
	 *            电话号码
	 * @return
	 */
	public static boolean isChinaPhoneNo(String phoneNo) {
		phoneNo = phoneNo.trim();
		if (phoneNo.length() < 11) {
			return false;
		} else {
			phoneNo = phoneNo.substring(phoneNo.length() - 11);

			String regEx = "^(13|15|18)\\d{9}$";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(phoneNo);
			return matcher.matches();
		}
	}
	
	

	/**
	 * 校验日期
	 * @param dateStr
	 * @return
	 */
	public static boolean checkDate(String dateStr) {
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?(((("
				+ "0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|"
				+ "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|"
				+ "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|"
				+ "([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|"
				+ "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(dateStr);
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	


	/**
	 * 判断数字是否在该范围内
	 * @param num
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean checkNumberBoundary(int num,int min ,int max){		
		boolean b =true;
		if(num<min||num>max){
			b=false;
		}
		return b;
	}
	
	/**
	 * 检测字符长度中文按2位计算
	 * @param str
	 * @param len
	 * @return
	 */
	public static boolean checkStrLength(String str,int len){
		boolean b=true;
		if (str!=null) {
			int _len = StringUtil.stringLength(str);
			if (_len > len) {
				b = false;
			}
		}else{
			b=true;
		}
		return b;
	}

}
