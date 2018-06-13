package com.ztessc.einvoice.controller.app;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.controller.BaseController;
import com.ztessc.einvoice.service.InvoiceService;
import com.ztessc.einvoice.util.HttpClientUtil;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;
import com.ztessc.einvoice.util.UuidUtil;
import com.ztessc.einvoice.util.WxCardSign;
import com.ztessc.einvoice.weixin.WeixinH5;

@RequestMapping(value = "/app")
@RestController
public class WeixinH5Controller extends BaseController {

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private WeixinH5 weixinH5;
	
    @PostMapping(value = "/v1/weixin/h5/getSignature")
    public PageData getSignature() {
		PageData result = new PageData();
		result.put("access_token", weixinH5.getAccessToken());
		result.put("api_ticket", weixinH5.getApiTicket());
		
		WxCardSign signer = new WxCardSign();
        signer.AddData(weixinH5.getApiTicket());
        signer.AddData(weixinH5.getAppId());
        signer.AddData(new Date().getTime()+"");
        signer.AddData(UuidUtil.getUUID());
        signer.AddData("INVOICE");
        result.put("signature", signer.GetSignature());
		return MessageUtil.getSuccessMessage(result);
    }
	
    /**
     * 首页---微信卡包获取
     * @author: 徐益森
     * @date: 2018年4月13日 下午2:21:47
     * @param {"item_list":[{"card_id":"aaa","encrypt_code":"bbb"},{"card_id":"ccc","encrypt_code":"ddd"}]}
     * @return PageData
     */
    @PostMapping(value = "/v1/weixin/h5/getInvoiceInfo")
    public PageData getInvoiceInfo() {
    	PageData data = this.getPageData();
    	List<PageData> itemList = data.transDataToPdList("item_list");
    	String url = "https://api.weixin.qq.com/card/invoice/reimburse/getinvoiceinfo?access_token={0}";
    	
    	List<String> list = new ArrayList<String>();
    	for(PageData param : itemList) {
    		url = MessageFormat.format(url, weixinH5.getAccessToken());
    		String invoiceInfo = HttpClientUtil.post(url,param.toString());
    		//解析发票详情，保存数据库，并下载pdf文件,需要判断是否已经保存数据库，如果以前有保存则过滤
			list.add(invoiceInfo);
    	}
    	
    	List<PageData> result = invoiceService.saveInvoiceFromWeixin(list);
    	
		return MessageUtil.getSuccessMessage(result);
    }
    
}
