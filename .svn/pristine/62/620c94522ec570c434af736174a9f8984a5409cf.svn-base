package com.ztessc.einvoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.util.PageData;

@Service
public class AppService {

	@Autowired
	private DaoSupport daoSupport;
	
	/**
	 * app版本检查，判断某个版本是否要强制更新
	 * @author: 徐益森
	 * @date: 2018年5月10日 上午10:06:12
	 * @param {"version":"1.0.0","platform":"android/ios"}
	 * @return PageData
	 */
	public PageData versionCheck(PageData pd) {
		//获取最新版本
		PageData lastVersion = daoSupport.findForPageData("AppMapper.findLastVersion", pd);
		if(lastVersion == null) {
			lastVersion = new PageData();
			lastVersion.put("isUpdate", "N");
		} else {
			
		}
		return lastVersion;
	}
	
}
