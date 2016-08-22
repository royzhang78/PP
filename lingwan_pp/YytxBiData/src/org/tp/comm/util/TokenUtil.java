package org.tp.comm.util;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年12月30日 下午5:48:24 
 * 类说明 
 */
public class TokenUtil {

	public static String getToken(long num){
		return Long.toHexString(num);
	}
	
	public static void main(String[] args) {
		System.out.println(TokenUtil.getToken(200000000+Long.valueOf(DateTimeUtil.getDateTime("yyMMddHH"))));
		System.out.println(TokenUtil.getToken(200000000));
	}
}
