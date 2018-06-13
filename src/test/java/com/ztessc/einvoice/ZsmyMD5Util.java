package com.ztessc.einvoice;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description MD5
 * @package com.zhy.zsmy.asset
 * @ClassName ZsmyMD5Util
 * @author 赵浩雲   zhaoyund@gmail.com
 * @CreateDate 2014-12-23 下午9:07:05
 * @Version V1.0 Copyright © 2014 MuYuan Company
 */
public final class ZsmyMD5Util {
	public static String getMd5(String str) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("utf-8"));
			byte b[] = md.digest();
			int i;

			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
