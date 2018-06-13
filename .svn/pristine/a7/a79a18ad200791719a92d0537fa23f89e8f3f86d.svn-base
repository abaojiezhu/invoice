package com.ztessc.einvoice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.util.DicUtil;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RequestMapping(value = {"/common","app/v1/common"})
@RestController
public class CommonController extends BaseController {

	/**
	 * 获取下拉框选项
	 * @author: 徐益森
	 * @date: 2018年4月19日 下午3:55:57
	 * @param {"type":"Z001000"}
	 * @return PageData
	 */
	@PostMapping(value="/getByParentKey")
	public PageData getByParentKey(){
		PageData params = this.getPageData();
		List<PageData> list = DicUtil.getByParentKey(params.getString("type"));
		params.put("list", list);
		return MessageUtil.getSuccessMessage(params);
	}
}
