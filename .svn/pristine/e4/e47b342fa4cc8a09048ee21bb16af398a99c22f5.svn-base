package com.ztessc.einvoice.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Copyright © 2016 zte. All rights reserved.
 * 
 * @Title: HttpClientUtil.java 
 * @Prject: hrcloudj
 * @Package: com.zte.hrcloudj.util 
 * @ClassName: HttpClientUtil 
 * @Description: 封装http请求，底层使用htpclient
 * @author: 徐益森   
 * @date: 2016年1月22日 下午4:37:39 
 * @version: V1.0
 */
public class HttpClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * get请求
	 * @author: 徐益森
	 * @date: 2018年4月13日 下午1:45:18
	 * @return String
	 */
	public static String get(String url) {  
		CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;  
        log.info("create httpget:" + url);  
        HttpGet get = new HttpGet(url);  
        body = invoke(httpclient, get);  
        return body;  
    }
	
	/**
	 * post请求(用于key-value格式的参数)
	 * @author: 徐益森
	 * @date: 2018年4月13日 下午1:45:41
	 * @param url
	 * @param params
	 * @return String
	 */
	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);
		
		//设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		post.setConfig(requestConfig);
		
		body = invoke(httpclient, post);
		return body;
	}
	
	/**
	 * post请求（用于请求json格式的参数）
	 * @author: 徐益森
	 * @date: 2018年4月13日 下午1:48:21
	 * @param url
	 * @param params
	 * @return String
	 */
	public static String post(String url, String params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httpPost = new HttpPost(url);// 创建httpPost     
        httpPost.setHeader("Accept", "application/json");   
        httpPost.setHeader("Content-Type", "application/json");  
        String charSet = "UTF-8";  
        StringEntity entity = new StringEntity(params, charSet);  
        httpPost.setEntity(entity);          
        CloseableHttpResponse response = null;  
        try {  
            response = httpclient.execute(httpPost);  
            StatusLine status = response.getStatusLine();  
            int state = status.getStatusCode();  
            if (state == HttpStatus.SC_OK) {  
                HttpEntity responseEntity = response.getEntity();  
                String jsonString = EntityUtils.toString(responseEntity);  
                return jsonString;  
            }else{ 
                 log.error("请求返回:"+state+"("+url+")");  
            }  
        } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	private static String invoke(CloseableHttpClient httpclient, HttpUriRequest request) {
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity,"UTF-8");
			return body;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (String key : params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
		}
		try {
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}
	
}
