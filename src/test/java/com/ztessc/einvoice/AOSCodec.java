package com.ztessc.einvoice;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @Description <b>加解密</b>
 * @package zhy.core.asset
 * @ClassName AOSCodec
 * @author 赵浩雲 zhaoyund@gmail.com
 * @CreateDate 2017年10月24日 22:57:25
 * @Version V1.0
 * @Company Muyuan Foods Co., Ltd.
 * @Copyright Mr.ZHY © 2017
 */
public class AOSCodec {
	
	//创建Base64对象,用于加密和解密;
	private final static Base64 base64 = new Base64(); 
	
	//加密时采用的编码方式;
	private final static String encoding = "UTF-8";
	
    

    /**
	 * 先用aes算法加密，在用base64编码后返回
	 * @param str 需要加密的字符串
	 * @param sKey 加密密钥
	 * @return    经过加密的字符串
	 */
	public static String encryptAes1(String str, String sKey) {
		// 声明加密后的结果字符串变量
		String result = str;
		if (str != null && str.length() > 0 && sKey != null && sKey.length() >= 8) {
			try { 
				//调用AES 加密数组的 encrypt方法，返回加密后的byte数组;
				byte[] encodeByte = encryptBasedAes1(str.getBytes(encoding),sKey);
				// 调用base64的编码方法，返回加密后的字符串;
				result = base64.encodeToString(encodeByte).trim();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			throw new RuntimeException("加密密钥不能为空且不能小于8位。");
		}
		return result;
	}

	/**
	 * 先用AES算法对byte[]数组加密
	 * @param byteSource 需要加密的数据
	 * @param sKey    加密密钥
	 * @return     经过加密的数据
	 * @throws Exception
	 */
	private static byte[] encryptBasedAes1(byte[] byteSource, String sKey) throws Exception {
        try {
                SecretKeySpec key = new SecretKeySpec(sKey.getBytes("UTF-8"), "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] result = cipher.doFinal(byteSource);
                return result;
        } catch (Exception e) {
                throw e;
        }
	}

	
	/**
	 * 先用base64解码，再用aes解密后返回
	 * 
	 * @param str 需要解密的字符串
	 * @param sKey 解密密钥
	 * @return   经过解密的字符串
	 */
	public static String decryptAes1(String str, String sKey) {
		String result = str;
		if (str != null && str.length() > 0 && sKey != null && sKey.length() >= 8) {
			try {  
				// 用base64进行编码，返回byte数组
				byte[] encodeByte = base64.decode(str);    
				// 调用DES 解密数组的decrypte方法，返回解密后的数组;
				byte[] decoder = decryptBasedAes1(encodeByte,sKey);
				// 对解密后的数组转化成字符串
				result = new String(decoder, encoding).trim();    
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 先用AES算法对byte[]数组解密
	 * @param byteSource 需要解密的数据
	 * @param sKey    解密密钥
	 * @return     经过解密的数据
	 * @throws Exception
	 */
	private static byte[] decryptBasedAes1(byte[] byteSource, String sKey)
			throws Exception {  
		try {
			SecretKeySpec key = new SecretKeySpec(sKey.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器     
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化     
			byte[] result = cipher.doFinal(byteSource);     
			return result; // 解密     
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	public static void main(String[] args) {
		String s1 = AOSCodec.encryptAes1("{\"username\":\"张三\",\"user_code\":\"0000123456789\",\"email\":\"123456789@qq.com\"}", "1111122222333334");
		String s2 = AOSCodec.decryptAes1(s1, "1111122222333334");
		
		System.out.println(s1);
		System.out.println(s2);
		
	}

}
