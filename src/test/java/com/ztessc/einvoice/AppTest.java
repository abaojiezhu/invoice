package com.ztessc.einvoice;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.ztessc.einvoice.util.HttpClientUtil;
import com.ztessc.einvoice.util.PageData;

public class AppTest {

	/*
	 * 发票结构
	 * 01,10,044001500111,91398776,415.57,20180111,14676717298231023492,21F8
	 * 总共8个数据
	 * 1:暂不清楚
	 * 2:发票种类代码: 
	 * 			01-增值税专用发票
	 * 			02-货物运输业增值税专用发票
	 * 			03-机动车销售统一发票
	 * 			04-增值税普通发票
	 * 			10-增值税电子发票
	 * 			11-卷式普通发票
	 * 			14-电子普通[通行费]发票
	 * 			20-国税
	 * 			30-地税
	 * 3:发票代码
	 * 4:发票号码
	 * 5:不含税金额
	 * 6:开票日期
	 * 7:校验码
	 * 8:随机产生的机密信息
	 */
	public void parse(String str) {
		
	}
	
	/** 
     * 本方法使用SHA1withRSA签名算法产生签名 
     * @param String priKey 签名时使用的私钥(16进制编码) 
     * @param String src    签名的原字符串 
     * @return String       签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。 
     */  
//    public static String generateSHA1withRSASigature(String priKey, String src)  
//    {  
//        try  
//        {  
//            Signature sigEng = Signature.getInstance("SHA1withRSA");  
//            byte[] pribyte = UtilString.hexStrToBytes(priKey.trim());  
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);  
//            KeyFactory fac = KeyFactory.getInstance("RSA");  
//            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);  
//            sigEng.initSign(privateKey);  
//            sigEng.update(src.getBytes());  
//            byte[] signature = sigEng.sign();  
//            return UtilString.bytesToHexStr(signature);  
//        }  
//        catch (Exception e)  
//        {  
//            e.printStackTrace();  
//            return null;  
//        }  
//    }  
    
	public static void main(String[] args) throws Exception {
		RsaEncrypt RsaEncrypt = new RsaEncrypt();
        // 加载公钥
        RsaEncrypt.loadPublicKey(RsaEncrypt.DEFAULT_PUBLIC_KEY);
        // 加载私钥
        RsaEncrypt.loadPrivateKey(RsaEncrypt.DEFAULT_PRIVATE_KEY);
        
		Map<String, String> params = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });
		
		String url = "https://external.yewifi.com/check/invocie-info";
		PageData content = new PageData();
		content.put("invoiceCode", "044001500111");
		content.put("invoiceNumber", "91398776");
		content.put("billTime", "20180111");
		content.put("checkCode", "023492");
		
		StringBuffer buff = new StringBuffer();
		buff.append("app_id=").append("2018011790165404").append("&")
			.append("biz_content=").append(content.toString()).append("&")
			.append("charset=").append("utf-8").append("&")
			.append("format=").append("json").append("&")
			.append("method=").append("").append("&")
			.append("sign_type=").append("RSA").append("&")
			.append("timestamp=").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("&")
			.append("version=").append("v1.0");
		
		System.out.println(buff);
		
		// 签名验证
        byte[] signbyte = RsaEncrypt.rsaSign(buff.toString(),RsaEncrypt.getPrivateKey());
        String sign = RsaEncrypt.ByteToHex(signbyte);
        System.out.println("签名-----：" + sign);
        
        sign = "aoflyNbYAGQyQfp0877QmYJpslyfcSfo7zfL8x7ds2W09P+JOw7Sz7uu1B87bDvv+ak5WLPxRyU/fT7uPGupQ5Xwu+J3Ny+I3N3A8LrrVBycKe0upSv+gtVb1X2fOZiXr3bimYnp+anXPLKqC00m8gLMuSRIuZ4QEPQ32jb+ZH4=";
        
        StringBuffer buff2 = new StringBuffer();
        buff2.append("app_id=").append("2017120476902176").append("&")
		.append("biz_content=").append(content.toString()).append("&")
		.append("charset=").append("utf-8").append("&")
		.append("format=").append("json").append("&")
		.append("method=").append("").append("&")
		.append("sign=").append(sign).append("&")
		.append("sign_type=").append("RSA").append("&")
		.append("timestamp=").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("&")
		.append("version=").append("v1.0");
		
        params.put("app_id", "2017120476902176");
        params.put("biz_content", content.toString());
        params.put("charset", "utf-8");
        params.put("format", "json");
        params.put("method", "");
        params.put("sign", sign);
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "v1.0");
System.out.println("params----------" + buff2);        
		String result = HttpClientUtil.post(url, buff2.toString());
		System.out.println(result);
		
	}
}
