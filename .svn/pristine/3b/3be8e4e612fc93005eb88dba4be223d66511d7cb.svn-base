package com.ztessc.einvoice.controller.app;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.controller.BaseController;
import com.ztessc.einvoice.service.InvoiceTitleService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;


@RequestMapping(value = "/app")
@RestController
public class InvoiceTitleAppController extends BaseController{
	
	
	@Autowired
	private InvoiceTitleService invoiceTitleService;
	
	/**
	 * 获取发票抬头所有数据
	 * @author:涂秋茂
	 * @date: 2018年4月18日 
	 * @param 
	 * @return PageData
	 */
    @PostMapping(value = "/v1/invoiceTitle/list")
    public PageData list() {
    	PageData params = this.getPageData();
    	List<PageData> list = invoiceTitleService.listInvoiceTitle(params);
        return MessageUtil.getSuccessMessage(list);
    }
    
    /**
     * @author Diy
     * @param {"id":"aaa"}
     * @return
     */
    @PostMapping(value = "/v1/invoiceTitle/findById")
    public PageData findById() {
    	
    	PageData params = this.getPageData();
    	
    	PageData data = invoiceTitleService.findById(params);
    	
        return MessageUtil.getSuccessMessage(data);
    }
    
    
    /**
     * 新增
     * @author TQM
     * @param  {"id":"aa","name":"名称","taxNumber":"税号","address":"地址","phoneNumber":"电话号码","bankName":"开户银行","bankAccount":"银行账号"}
     * 
     */
    @PostMapping(value = "/v1/invoiceTitle/saveOrUpdate")
	public PageData save() {
		PageData params = this.getPageData();
		String id = params.getString("id");
		if(StringUtils.isEmpty(id)){
			invoiceTitleService.save(params);
		}else{
			invoiceTitleService.update(params);
		}
		
		
		
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_SAVE);
	}
	
	
	/**
	 * 逻辑删除
	 * @author TQM
	 * @param {"id":"aa"}
	 */
    @PostMapping(value = "/v1/invoiceTitle/delete")
	public PageData delete() {
		PageData params = this.getPageData();
		
		invoiceTitleService.delete(params);
		
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_DELETE);
	}
	
}
