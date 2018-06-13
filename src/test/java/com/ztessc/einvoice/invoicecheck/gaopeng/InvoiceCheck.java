package com.ztessc.einvoice.invoicecheck.gaopeng;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.ztessc.einvoice.util.HttpClientUtil;

public class InvoiceCheck {

	public static void main(String[] args) {
		String url = "https://external.yewifi.com/check/invoice-info";
		Map<String, String> content = new LinkedHashMap<String, String>();
		content.put("invoiceCode", "044001500111");
		content.put("invoiceNumber", "91398776");
		content.put("billTime", "2018-01-11");
//		content.put("checkCode", "803011");
//		content.put("invoiceAmount", "2510.7");
		
        String sign = "EkKic2akwkrD+zgBgcL3Pz8iF1Yxx4XL5OMf31e1Dh9b41AIedjLaZzoeSQfQEvPEWTyVXFbSJ+yR5MI/ISQyArrFcGhBFN1WqAHoI1IHa6vcH5cX7uyv42KIr0GjO5MiNclqkfUOep2nIe0oXSKbrPv6s9nN9189B2jzDmkr+A=";
        
        Map<String, String> params = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });
        params.put("app_id", "2017120476902176");
        params.put("biz_content", JSON.toJSONString(content));
        params.put("charset", "utf-8");
        params.put("format", "json");
        params.put("method", "");
        params.put("sign", sign);
        params.put("sign_type", "RSA");
        params.put("timestamp", "2018-04-18 10:15:44");
        params.put("version", "v1.0");
		
       String json = JSON.toJSONString(params);
         
System.out.println("params----------" + json);        
		String result = HttpClientUtil.post(url, json);
		System.out.println(result);
	}
}
