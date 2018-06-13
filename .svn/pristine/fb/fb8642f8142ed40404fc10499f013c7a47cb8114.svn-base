package com.ztessc.einvoice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ztessc.einvoice.service.InvoiceService;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RequestMapping(value = "/invoice")
@RestController
public class InvoiceController extends BaseController{
	
	@Autowired
	private InvoiceService invoiceService;
	
	/**
	 * 获取发票查询数据
	 * @author:涂秋茂
	 * @date: 2018年4月17日 
	 * @param {"billingNo":"aaa","billingCode":"aaa","buyerName":"aa","billingStartTime":"","billingEndTime":"","validState":"","pageNum":"0","pageSize":"10"}
	 * @return PageData
	 */
	@PostMapping(value = "/list")
    public PageData list() {
    	PageData params = this.getPageData();
    	String pageNum = params.getString("pageNum");
    	String pageSize = params.getString("pageSize");
    	Page<Object> page = PageHelper.startPage(Integer.parseInt(pageNum) , Integer.parseInt(pageSize));
    	List<PageData> list = invoiceService.listInvoiceInfo(params);
    	
		PageData pageResult = this.getPageResult(page, list);
		
        return MessageUtil.getSuccessMessage(pageResult);
    	
    	
    }
    
    
    

}
