package com.ztessc.einvoice;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.util.Base64Util;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.HmacSHA1Util;
import com.ztessc.einvoice.util.HttpClientUtil;
import com.ztessc.einvoice.util.PageData;



public class Test3 {

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
     * @param encryptText 被签名的字符串  
     * @param encryptKey  密钥  
	 * @return 签名
	 */
	public static String encrypt(String encryptText, String encryptKey) {
		byte[] keyBytes;
		try {
			keyBytes = encryptKey.getBytes("UTF-8");

			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
			// 生成一个指定 Mac 算法 的 Mac 对象
			Mac mac = Mac.getInstance("HmacSHA1");
			// 用给定密钥初始化 Mac 对象
			mac.init(secretKey);
			// 完成 Mac 操作
			byte[] encrypted = mac.doFinal(encryptText.getBytes("UTF-8"));
			return Base64Util.toBase64String(encrypted);
		} catch (Exception e) {
			throw new RuntimeException("HMAC-SHA1签名异常", e);
		}
	}
	
	public static void main(String[] args) {
		String url = "http://192.168.1.100:8080/api/invoice/list";
		
		String data = "{\"billingTime\":\"2018-05\"}";
		String encryptKey = "xuyisen";
		String sign = encrypt(data, encryptKey);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("appId", "xys");
		params.put("sign", sign);
		params.put("data", data);
		
		String result = HttpClientUtil.post(url, params);
		
		System.out.println(result);
		
	}
}
