package com.ztessc.einvoice.qyweixin;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztessc.einvoice.util.HttpClientUtil;
import com.ztessc.einvoice.util.SHA1Util;
import com.ztessc.einvoice.util.UuidUtil;

public class Main {

	private static String corpID = "ww7bff64bfb5ad22c8";
	private static String agentId = "1000002";
	private static String secret = "BbKYsKrPu35ukEpwRCleGB1qLRV1VHDcxabZbd13J9A";
	
	public static String getSuccessToken() {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";
		url = MessageFormat.format(url, corpID, secret);
		String result = HttpClientUtil.get(url);
		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject.getString("access_token");
	}
	
	public static String getTicket(String successToken) {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token={0}";
		url = MessageFormat.format(url, successToken);
		String result = HttpClientUtil.get(url);
		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject.getString("ticket");
	}
	
	public static String getSignature(String ticket) {
		StringBuilder sb = new StringBuilder();
		sb.append("jsapi_ticket=").append(ticket).append("&")
		  .append("noncestr=").append(UuidUtil.getUUID()).append("&")
		  .append("timestamp=").append(System.currentTimeMillis()).append("&")
		  .append("url=").append("http://www.ztessc.com.cn");
		
		String sha1 = SHA1Util.getSha1(sb.toString());
		
		return sha1;
	}
	
	public static void main(String[] args) {
		
//		String successToken = getSuccessToken();
//		System.out.println(successToken);
		
//		String successToken = "LbhbQyErSdeLlzaBwdEE9c_x_i6xri99rwwub3IVv6x683DI224oedYYSNzJ3QrvUNCNogJGcFjXIJgmkuJyiwPcUMQiNQxswFMD--aPHXOBQZeoU25z1LDIevmnQAdtk39a8yGRrlz9z3w3qYCk-MiIw_ST35OC2GCZG7oJWPVBQ_kx7yIPY4-hfxwT22YEV6W8OtgEsFl5ad-KlOJxlQ";
//		
//		String ticket = getTicket(successToken);
//		System.out.println(ticket);
//		
//		String signature = getSignature(ticket);
//		System.out.println(signature);
		
		String sha1 = SHA1Util.getSha1("jsapi_ticket=HoagFKDcsGMVCIY2vOjf9qxRGZsHOBNSU2x4Q5DpSzhSJODfErdZqxDOHzzTmN10bRPKwLYIg-s2KkaZlSftoQ&noncestr=bc8710fa36664ad0aed48ce1f2f4ce4b&timestamp=1528703245934&url=http://www.ztessc.com.cn");
		System.out.println(sha1);
		
	}
	
}
