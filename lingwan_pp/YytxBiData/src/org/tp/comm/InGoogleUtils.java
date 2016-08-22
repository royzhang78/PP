/**
 * 
 */
package org.tp.comm;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import jodd.log.Logger;
import jodd.log.LoggerFactory;
import jodd.util.Base64;

/**
 * @author shyboy(shgbog.shen@ifreeteam.com)
 * 
 */
public class InGoogleUtils {
	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InGoogleUtils.class);

	private static final String KEY_FACTORY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

	/**
	 * 根据游戏的public key验证支付时从Google Market返回的signedData与signature的值是否对应
	 * 
	 * @param publicKey
	 *            ：配置在Google Play开发者平台上的公钥
	 * @param signedData
	 *            ：支付成功时响应的物品信息
	 * @param signature
	 *            ：已加密后的签名
	 * @return boolean：true 验证成功<br/>
	 *         false 验证失败
	 */
	public static boolean verify(PublicKey publicKey, String signedData, String signature) {
		try {
			Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
			sig.initVerify(publicKey);
			sig.update(signedData.getBytes());
			return sig.verify(Base64.decode(signature));
		} catch (Exception e) {
			logger.error("", e);
		}
		return false;
	}

	/**
	 * 根据已加密的public key获取PublicKey对象
	 * 
	 * @param encodedPublicKey
	 *            ：已加密的key，对应于在Google Play开发者平台上所配置的
	 * @return PublicKey
	 */
	public static PublicKey generatePublicKey(String encodedPublicKey) {
		try {
			byte[] decodedKey = Base64.decode(encodedPublicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
			return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}
