package org.tp.comm.util;



import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesUtil {

   private final static String DES = "DES";

   public static void main(String[] args) throws Exception {
       String data = "123 456";
       String key = "12345678";
       System.err.println(encrypt("cagent=TST_AGIN/\\\\/loginname=TSTAGIN52082/\\\\/method=lg/\\\\/actype=1/\\\\/password=1q2w3e4r5t/\\\\/oddtype=A/\\\\/cur=CNY", key));
       System.err.println(decrypt("XEWtB/KuTH9NvIzgPuC0VAKUSHHN7XApQkTQjKtCq9rQ7bOuue4h0vpF9WZaVZg6MuPzHmEhRVVsYvro4I0pGoOgO6wNqAwOwrXusgQBfQX/yOQ9J9v2LJpv0lWwSUzzzVi7psBC5Xxjay61bzo+r6hb2yD0F6B3", key));

   }
    
   /**
    * Description 根据键值进行加密
    * @param data 
    * @param key  加密键byte数组
    * @return
    * @throws Exception
    */
   public static String encrypt(String data, String key) throws Exception {
       byte[] bt = encrypt(data.getBytes(), key.getBytes());
       String strs = new BASE64Encoder().encode(bt);
       return strs;
   }

   /**
    * Description 根据键值进行解密
    * @param data
    * @param key  加密键byte数组
    * @return
    * @throws IOException
    * @throws Exception
    */
   public static String decrypt(String data, String key) throws IOException,
           Exception {
       if (data == null)
           return null;
       BASE64Decoder decoder = new BASE64Decoder();
       byte[] buf = decoder.decodeBuffer(data);
       byte[] bt = decrypt(buf,key.getBytes());
       return new String(bt);
   }

   /**
    * Description 根据键值进行加密
    * @param data
    * @param key  加密键byte数组
    * @return
    * @throws Exception
    */
   private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
       // 生成一个可信任的随机数源
       SecureRandom sr = new SecureRandom();

       // 从原始密钥数据创建DESKeySpec对象
       DESKeySpec dks = new DESKeySpec(key);

       // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
       SecretKey securekey = keyFactory.generateSecret(dks);

       // Cipher对象实际完成加密操作
       Cipher cipher = Cipher.getInstance(DES);

       // 用密钥初始化Cipher对象
       cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

       return cipher.doFinal(data);
   }
    
    
   /**
    * Description 根据键值进行解密
    * @param data
    * @param key  加密键byte数组
    * @return
    * @throws Exception
    */
   private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
       // 生成一个可信任的随机数源
       SecureRandom sr = new SecureRandom();

       // 从原始密钥数据创建DESKeySpec对象
       DESKeySpec dks = new DESKeySpec(key);

       // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
       SecretKey securekey = keyFactory.generateSecret(dks);

       // Cipher对象实际完成解密操作
       Cipher cipher = Cipher.getInstance(DES);

       // 用密钥初始化Cipher对象
       cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

       return cipher.doFinal(data);
   }
}