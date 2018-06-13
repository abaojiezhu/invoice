package com.ztessc.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.util.PageData;

@Service
public class LogService {

	@Autowired
	private DaoSupport daoSupport;
	
	@Transactional
	public void save(PageData pd) {
		daoSupport.save("LogMapper.save", pd);
	}
	
	public List<PageData> listLogInfo(PageData pd) {
		return daoSupport.listForPageData("LogMapper.listLogInfo", pd);
	}

	public PageData findUrl(String url) {
		return daoSupport.findForPageData("LogMapper.findUrl", url);
	}
	
	public PageData findLastTimeByUser(PageData pd) {
		return daoSupport.findForPageData("LogMapper.findLastTimeByUser", pd);
	}
	
//	@Transactional
//	public PageData saveUrl(PageData pd) {
//		return daoSupport.findForPageData("LogMapper.saveUrl", pd);
//	}
	
}
