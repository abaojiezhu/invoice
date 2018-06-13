package com.ztessc.einvoice.weixin;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ztessc.einvoice.util.Log;

@Component
public class WeixinApp {

	private static Logger log = Log.get();
	
	private static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	
	private static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=wx_card";
	
	@Autowired
	private Environment env;
	
	private static String accessToken;
	
	private static String apiTicket;
	
	public void refresh() {
		try {
			refreshAccessToken();
			refreshApiTicket();
		} catch (Exception e) {
			log.error("微信-获取accessToken或ticket失败", e);
		}
	}

	private void refreshAccessToken() {
		accessToken = WeixinUtil.refreshAccessToken(TOKEN_URL, getAppId(), getSecret());
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
	
	public String getApiTicket() {
		if(StringUtils.isBlank(apiTicket)) {
			refreshApiTicket();
		}
		return apiTicket;
	}
	
	public String getAppId() {
		return env.getProperty("weixin.appid");
	}
	
	public String getSecret() {
		return env.getProperty("weixin.secret");
	}
}
