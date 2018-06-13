package com.ztessc.einvoice.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ztessc.einvoice.service.LogService;
import com.ztessc.einvoice.util.DateUtil;
import com.ztessc.einvoice.util.HmacSHA1Util;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;
import com.ztessc.einvoice.util.UuidUtil;


public class SignatureValidInterceptor extends HandlerInterceptorAdapter {
	
	Logger log = Log.get();

	@Autowired
	private LogService logService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception  {
		//请求开始时间
		long beginTime = System.currentTimeMillis();
		
		PageData pd = new PageData(request);

		String data = (String) pd.getString("data");
		String appId = (String) pd.getString("appId");
		String sign = (String) pd.getString("sign");

		if(!HmacSHA1Util.checkSign(data, appId, sign)){
			
			String errorMsg = "API签名未通过校验";
			log.error(errorMsg);
			
			String sessionId = request.getSession().getId();
			
			//请求url地址
			String url = parseURL(request.getRequestURL().toString());
			
			//客户端访问IP地址
			String ip = this.getIpAddr(request);
			
			String requestParams = this.getRequestParams(request);
			
			//请求结束时间
			long endTime = System.currentTimeMillis();
			
			//记录日志详情
			PageData obj = new PageData();
			obj.put("id", UuidUtil.getUUID());
			obj.put("ip", ip);
			obj.put("beginTime", new Date(beginTime));
			obj.put("endTime", new Date(endTime));
			obj.put("useTime", (endTime-beginTime));
			obj.put("url", url);
			obj.put("operaResult", "Z006002");
			obj.put("exceptionMsg", errorMsg);
			obj.put("exceptionType", "Z007002");
			obj.put("requestParams", requestParams);
			obj.put("sessionId", sessionId);
			obj.put("createdDt", DateUtil.getCurrentDateTime());
			logService.save(obj);
			
	        response.setContentType("application/json; charset=utf-8");
	        response.getWriter().append(MessageUtil.getErrorMessage(errorMsg).toString());
	        return false;
		}

		return super.preHandle(request, response, handler);
	}
	
	/**
	 * 请求参数
	 * @author: 徐益森
	 * @date: 2018年4月21日 上午9:24:35
	 * @param {}
	 * @return String
	 */
	private String getRequestParams(HttpServletRequest request) {
		PageData requestParams = new PageData(request);
		String params = null;
		if(requestParams != null && requestParams.size() > 0) {
			params = requestParams.toString();
			if(StringUtils.isNotBlank(params) && params.length() > 5000) {
				params = params.substring(0,5000);
			}
		}
		return params;
	}
	
	/**
	 * 客户端IP
	 * @author: 徐益森
	 * @date: 2018年4月20日 上午11:14:11
	 * @param {}
	 * @return String
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){  
                //根据网卡取本机配置的IP  
                InetAddress inet=null;  
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (UnknownHostException e) {  
                    e.printStackTrace();  
                }  
                ip= inet.getHostAddress();  
            }
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15  
            if(ip.indexOf(",")>0){  
            	ip = ip.substring(0,ip.indexOf(","));  
            }  
        }
		return ip;
	}
	
	/**
	 * 清理url中的jsessionid、参数等内容
	 * @author: 徐益森
	 * @date: 2018年4月20日 上午11:14:01
	 * @param {}
	 * @return String
	 */
	private String parseURL(String url){
		if(url != null){
			if(url.indexOf(";") != -1){
				url = url.split(";")[0];
			}
			if(url.indexOf("#") != -1){
				url = url.split("#")[0];
			}
			if(url.indexOf("?") != -1){
				url = url.split("\\?")[0];
			}
			if(url.endsWith("/")){
				url = url.substring(0, url.length()-1);
			}
			while(url.contains("//")){
				url = url.replace("//", "/");
			}
			url = url.replace(":/", "://");
			
			if(url.startsWith("http://")) {
				url = url.substring(7);
			}
			if(url.startsWith("https://")) {
				url = url.substring(8);
			}
		}
		return url;
	}
}
