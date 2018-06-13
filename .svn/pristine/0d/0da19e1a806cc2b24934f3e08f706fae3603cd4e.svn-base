package com.ztessc.einvoice.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.ztessc.einvoice.util.PageData;

public class BaseController {

	/**
	 * 得到PageData
	 */
	public PageData getPageData(){

		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/** 
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

	protected PageData getPageResult(Page<Object> page, List<PageData> list) {
		PageData result = new PageData();
		result.put("list", list);
		result.put("total", page.getTotal());
		return result;
	}
	
}
