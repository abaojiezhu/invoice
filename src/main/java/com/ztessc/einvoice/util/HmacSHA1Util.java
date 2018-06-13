package com.ztessc.einvoice.util;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.exception.BizException;

public class HmacSHA1Util {

    private static final String MAC_NAME = "HmacSHA1";
    
	private static DaoSupport daoSupport = (DaoSupport) ApplicationContextUtil.getContext().getBean("daoSupport");
    
    private static final Map<String, String> APP_ID_ENCRYPT_KEY_MAP = new HashMap<String, String>();
    
    /**  
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
     * @param encryptText 被签名的字符串  
     * @param encryptKey  密钥  
     * @return  
     * @throws Exception  
     */    
	public static String encrypt(String encryptText, String encryptKey) {
		byte[] keyBytes;
		try {
			keyBytes = encryptKey.getBytes(Const.DEFAULT_CHARSET_NAME);

			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKey secretKey = new SecretKeySpec(keyBytes, MAC_NAME);
			// 生成一个指定 Mac 算法 的 Mac 对象
			Mac mac = Mac.getInstance(MAC_NAME);
			// 用给定密钥初始化 Mac 对象
			mac.init(secretKey);
			// 完成 Mac 操作
			byte[] encrypted = mac.doFinal(encryptText.getBytes(Const.DEFAULT_CHARSET_NAME));
			return Base64Util.toBase64String(encrypted);
		} catch (Exception e) {
			throw new BizException("HMAC-SHA1签名异常", e);
		}
	}
	
    /**  
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
     * @param encryptText 被签名的字符串  
     * @param encryptKey  密钥  
     * @return  
     * @throws Exception  
     */    
	public static String encryptServer(String encryptText, String appId) {
		String encryptKey = getEncryptKey(appId);
		return encrypt(encryptText, encryptKey);
	}
	
	public static boolean checkSign(String encryptText, String appId, String sign){
		if(StringUtils.isBlank(appId) || StringUtils.isBlank(sign) || StringUtils.isBlank(encryptText)){
			return false;
		}
		String encrypt = encryptServer(encryptText, appId);
		System.out.println("encryptText-------------" + encryptText);		
		System.out.println("appId-------------" + appId);	
		System.out.println("encrypt-------------" + encrypt);	
		System.out.println("sign-------------" + sign);	
		return sign.equals(encrypt);
	}
	
	private static String getEncryptKey(String appId) {
		//先从缓存中获取
		String encryptKey = APP_ID_ENCRYPT_KEY_MAP.get(appId);
		//如果缓存中没有去数据库查询
		if(StringUtils.isBlank(encryptKey)) {
			PageData data = daoSupport.findForPageData("EncryptKeyMapper.findByAppId", appId);
			//如果数据库中也不存在，则签名校验失败
			if(data == null || data.size() == 0) {
//				throw new BizException("API签名未通过校验");
				return null;
			}
			//如果存在放入缓存中，方便下次直接从缓存中取
			APP_ID_ENCRYPT_KEY_MAP.put(data.getString("appId"), data.getString("encryptKey"));
			encryptKey = data.getString("encryptKey");
		}
		return encryptKey;
	}
	
}
