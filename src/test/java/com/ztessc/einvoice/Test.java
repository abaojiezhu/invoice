package com.ztessc.einvoice;

import java.util.HashMap;
import java.util.Map;

import com.ztessc.einvoice.util.HmacSHA1Util;
import com.ztessc.einvoice.util.HttpClientUtil;
import com.ztessc.einvoice.util.PageData;

public class Test {

	public static void main(String[] args) {
		String url = "http://192.168.1.132:8081/api/invoice/check";
		
		PageData data = new PageData();
		//-----------------------专用发票------------------------------
//		data.put("billingNo", "4300171131");
//		data.put("billingCode", "03298083");
//		data.put("billingTime", "2018-04-26");
////		data.put("feeWithoutTax", "4200.16");
		
		//-----------------------电子发票------------------------------
		data.put("billingNo", "012001700178");
		data.put("billingCode", "81818178");
		data.put("billingTime", "2018-05-02");
		data.put("checkCode", "243531");
		
		//-----------------------普通发票------------------------------
//		data.put("billingNo", "4403172320");
//		data.put("billingCode", "36950925");
//		data.put("billingTime", "2018-04-15");
//		data.put("checkCode", "803011");
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("data", data.toString());
		params.put("appId", "c6ebaffd99cd49a184dadad6e46ccb09");
		params.put("sign", HmacSHA1Util.encrypt(data.toString(),"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKRcJx8Ep0DPqBDfKawdwnt5im0Q7k+HPYJs6rOr4xZJLyz+MNxPfA601RzNwhVTlmJjfr1S5RCdmGvhbgG4SGUCAwEAAQ=="));
System.out.println("params===========" + params);		
		String result = HttpClientUtil.post(url, params);
		
		System.out.println("result======1" + result);
		
	}
}
