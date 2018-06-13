package com.ztessc.einvoice.weixin;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ztessc.einvoice.util.Log;

@Component
public class WeixinWork {

	private static Logger log = Log.get();
	
	/**access_token*/
	public static String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";
	/**jsapi ticket*/
	public static String TICKET_JS_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token={0}";
	/**电子发票ticket*/
	public static String TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/ticket/get?access_token={0}&type=wx_card";
	
	@Autowired
	private Environment env;
	
	private static String accessToken;
	
	private static String apiTicket;
	
	private static String jsapiTicket;
	
	public void refresh() {
		try {
			refreshAccessToken();
			refreshJsApiTicket();
			refreshApiTicket();
		} catch (Exception e) {
			log.error("企业微信-获取accessToken或ticket失败", e);
		}
	}
	
	private void refreshAccessToken() {
		accessToken = WeixinUtil.refreshAccessToken(TOKEN_URL, getCorpId(), getSecret());
	}

	private void refreshJsApiTicket() {
		jsapiTicket = WeixinUtil.refreshApiTicket(TICKET_JS_URL, getAccessToken());
	}
	
	private void refreshApiTicket() {
		apiTicket = WeixinUtil.refreshApiTicket(TICKET_URL, getAccessToken());
	}
	
	public String getAccessToken() {
		if(StringUtils.isBlank(accessToken)) {
			refreshAccessToken();
		}
		return accessToken;
	}
	
	public String getJsApiTicket() {
		if(StringUtils.isBlank(jsapiTicket)) {
			refreshJsApiTicket();
		}
		return jsapiTicket;
	}
	
	public String getApiTicket() {
		if(StringUtils.isBlank(apiTicket)) {
			refreshApiTicket();
		}
		return apiTicket;
	}
	
	public String getCorpId() {
		return env.getProperty("weixin.work.corpid");
	}
	
	public String getSecret() {
		return env.getProperty("weixin.work.secret");
	}
	
	public String getUrl() {
		return env.getProperty("weixin.work.url");
	}
}
