package com.ztessc.einvoice.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.ztessc.einvoice.exception.BizException;

public class Base64Util {

	public static String encode(String data) throws UnsupportedEncodingException {
		return toBase64String(data.getBytes(Const.DEFAULT_CHARSET_NAME));
	}

	public static String decode(String base64String) {
		try {
			return new String(fromBase64String(base64String),Const.DEFAULT_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			throw new BizException("base64解码异常",e);
		}
	}
	
	public static String toBase64String(byte[] binaryData) {
		try {
			return new String(Base64.encodeBase64(binaryData),Const.DEFAULT_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			throw new BizException("base64编码异常",e);
		}
	}
	    
	public static byte[] fromBase64String(String base64String) {
		return Base64.decodeBase64(base64String);
	}
	
}
