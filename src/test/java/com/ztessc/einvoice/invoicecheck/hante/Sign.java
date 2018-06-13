package com.ztessc.einvoice.invoicecheck.hante;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.ztessc.einvoice.util.MD5;

public class Sign {

	// main调用方法
	public static void main(String[] args) {

		String secretKey = "omlp@ssW0rd";// 私钥
		String application = "91440300MA5EXWHW6FTT001";// 应用id
		String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map = new TreeMap<String, Object>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });
		
		String data = "{\"fapdm\":\"044001500111\",\"faphm:\"91398776\",\"faplx\":\"026\",\"jine\":\"415.57\",\"kaiprq\":\"20180111\",\"waibid\":\"1525768830046\",\"xiaoym\":\"14676717298231023492\"}";
		map.put("application", application);
		map.put("data ", data);
		map.put("request_time", timestamp);
System.out.println("map=======" + map);		
		String digest = sign(map, secretKey);
		System.out.println(digest);
	}

	// 得到签名值
	public static String sign(Map<String, Object> map, String secretKey) {
		StringBuilder contentBuffer = new StringBuilder();
		Object[] signParamArray = map.keySet().toArray();
		Arrays.sort(signParamArray);
		for (Object key : signParamArray) {
			Object value = map.get(key);
			if (!"digest".equals(key) && value != null) {
				contentBuffer.append(key + "=" + value + "&");
			}
		}
		return MD5.md5(contentBuffer.substring(0,contentBuffer.length() - 1) + secretKey);
	}

}
