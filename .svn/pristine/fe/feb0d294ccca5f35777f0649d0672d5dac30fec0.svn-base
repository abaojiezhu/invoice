package com.ztessc.einvoice.weixin;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztessc.einvoice.util.HttpClientUtil;

public class WeixinUtil {

	public static String refreshAccessToken(String url, String appid, String secret) {
		url = MessageFormat.format(url, appid, secret);
		String access_token = HttpClientUtil.get(url);
		JSONObject jsonObject = JSON.parseObject(access_token);
		return jsonObject.getString("access_token");
	}
	
	public static String refreshApiTicket(String url, String accessToken) {
		url = MessageFormat.format(url, accessToken);
		String api_ticket = HttpClientUtil.get(url);
		JSONObject jsonObject = JSON.parseObject(api_ticket);
		return jsonObject.getString("ticket");
	}

}
