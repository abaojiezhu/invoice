package com.ztessc.einvoice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test2 {

	public static void main(String[] args) {
		String str = "{\"errcode\":0,\"errmsg\":\"ok\",\"card_id\":\"pDe7ajrY4G5z_SIDSauDkLSuF9NI\",\"openid\":\"owJYI0nOT80lLIElOZWcAMMHWDs4\",\"type\":\"增值税电子普通发票\",\"payee\":\"滴滴出行科技有限公司\",\"detail\":\"\",\"user_info\":{\"fee\":1206,\"title\":\"前海追溯（深圳）科技有限公司\",\"billing_time\":1523841823,\"billing_no\":\"012001700211\",\"billing_code\":\"16868578\",\"info\":[{\"name\":\"*运输服务*客运服务费\",\"price\":1171}],\"fee_without_tax\":1171,\"tax\":35,\"detail\":\"项目\",\"pdf_url\":\"https://mp.weixin.qq.com//intp//invoice//getpdf?action=media_pdf&media_key=SEN3Qi9MWTBNdEJvdUJ4ZmkyNU43ZnpPc0M0KyNvIER8dzllNFo6XHgrTWgtbHxYRDppW3M9Ji49Tzg\",\"reimburse_status\":\"INVOICE_REIMBURSE_INIT\",\"order_id\":\"6AdtK9iVec96TGsvK6\",\"s_pdf_media_id\":\"428148326270028\",\"check_code\":\"16146306407044833985\",\"buyer_number\":\"91440300MA5EMXK30P\",\"buyer_address_and_phone\":\"\",\"buyer_bank_account\":\"\",\"seller_number\":\"911201163409833307\",\"seller_address_and_phone\":\"天津经济技术开发区南港工业区综合服务区办公楼C座103室12单元022-59002850\",\"seller_bank_account\":\"招商银行股份有限公司天津自由贸易试验区分行122905939910401\"}}";
		System.out.println(str.length());
		
		String str2 = "{\"invoiceCode\":\"044001500111\",\"invoiceDataCode\":\"044001500111\",\"invoiceNumber\":\"91398776\",\"invoiceTypeName\":\"广东增值税（电子普通发票）\",\"invoiceTypeCode\":\"10\",\"checkDate\":\"2018-04-16 12:27:04\",\"checkNum\":\"2\",\"billTime\":\"2018-01-11\",\"billingTime\":\"2018-01-11\",\"salesName\":\"广州市天河区珠江新城探鱼餐厅\",\"salesTaxpayerNum\":\"36040319760812181801\",\"salesTaxpayerAddress\":\"广东省广州市天河区花城大道89号负一层3001号商铺（部位：A23）,020-85208602\",\"salesTaxpayerBankAccount\":\"中国民生银行股份有限公司广州天河支行697599176\",\"purchaserName\":\"深圳中兴网信科技有限公司\",\"taxpayerNumber\":\"9144030068537795OT\",\"taxpayerAddressOrId\":\" \",\"taxpayerBankAccount\":\" \",\"checkCode\":\"14676717298231023492\",\"totalTaxNum\":\"24.93\",\"totalTaxSum\":\"440.50\",\"invoiceRemarks\":\" \",\"taxDiskCode\":\"499925643071\",\"totalAmount\":\"415.57\",\"voidMark\":\"0\",\"isBillMark\":\"N\",\"invoiceDetailData\":[{\"lineNum\":\"1\",\"goodserviceName\":\"*餐饮服务*餐饮费\",\"unit\":\" \",\"number\":\"1\",\"price\":\"415.566\",\"sum\":\"415.57\",\"model\":\" \",\"taxRate\":\"6%\",\"tax\":\"24.93\",\"isBillLine\":\"N\"}]}";
		System.out.println(str2.length());
		
		double d = 9/7d;
		System.out.println(d);
		
		System.out.println(String.format("%.2f", Double.valueOf("1150")/100d));
		
		String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1523841564L*1000));
		System.out.println(format);
		
		Map<String,Object> jsonObject = JSON.parseObject(str);
		JSONObject userInfo = (JSONObject) jsonObject.get("user_info");
		JSONArray info = (JSONArray) userInfo.get("info");
		System.out.println(userInfo);
		System.out.println(info);
		
//		FileDownload.fileDownload("http://img3.duitang.com/uploads/item/201511/07/20151107152913_LxnKN.jpeg", "D:/20180419.jpeg");
	}
}
