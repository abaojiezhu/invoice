package com.ztessc.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.util.PageData;

@Service
public class TestService {

	@Autowired
	private DaoSupport daoSupport;
	
	public List<PageData> findAll(PageData pd) {
		return daoSupport.listForPageData("TestMapper.findAll", pd);
	}
	
	@Transactional
	public void save(PageData pd) {
		daoSupport.save("TestMapper.save", pd);
//		System.out.println(1/0);
	}
}
