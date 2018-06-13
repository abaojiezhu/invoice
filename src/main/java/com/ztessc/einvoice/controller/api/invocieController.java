package com.ztessc.einvoice.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.controller.BaseController;
import com.ztessc.einvoice.service.InvoiceService;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;


@RequestMapping(value = "/api/invoice")
@RestController
public class invocieController extends BaseController{
	@Autowired
	private InvoiceService invoiceService;
	
	/**
	 * 获取已验证后的发票列表信息
	 * @param {"data":{"userId":"aaa","billingTime":"2018-01"},"sign":"abcd","appId":"dddd"}
	 * @return
	 */
	@RequestMapping("/list")
	public PageData listInvoiceResult(HttpServletRequest request){
		PageData data = PageData.parseJson(request.getParameter("data"));
		List<PageData> listInvoice = invoiceService.listInvoiceResult(data);
		return MessageUtil.getSuccessMessage(listInvoice);
	}

}
