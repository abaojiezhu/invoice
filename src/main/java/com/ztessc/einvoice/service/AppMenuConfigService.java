package com.ztessc.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.util.PageData;

/**
 * APP 菜单配置
 * Copyright © 2018 zte. All rights reserved.
 * @Description: TODO
 * @Title: AppMenuConfigService.java 
 * @Prject: pl
 * @Package: com.zte.pl.service 
 * @ClassName: AppMenuConfigService 
 * @author: 徐益森   
 * @date: 2018年4月11日 上午9:53:02 
 * @version: V1.0.0
 */
@Service
public class AppMenuConfigService {

	@Autowired
	private DaoSupport daoSupport;
	
	/**
	 * 获取所有数据
	 * @author: 徐益森
	 * @date: 2018年4月11日 下午6:16:56
	 * @return List<PageData>
	 */
	public List<PageData> listMenuConfigs() {
		return daoSupport.listForPageData("AppMenuConfigMapper.listMenuConfigs", null);
	}
	/**
	 * 获取app菜单
	 * @author: 徐益森
	 * @date: 2018年4月11日 上午9:45:13
	 * @return List<PageData>
	 */
	public List<PageData> listMenus() {
		return daoSupport.listForPageData("AppMenuConfigMapper.listMenus", null);
	}
	
	
	/**
	 * 修改
	 * @author: 徐益森
	 * @date: 2018年4月11日 上午9:47:43
	 * @param {"id":"aaa","imgName":"","imgDesc":"","fileName":"","realName":"","enabled":""}
	 * @return void
	 */
	@Transactional
	public void update(PageData pd) {
		//验证名称是否重复
		PageData data = this.findByName(pd);
		if(data != null && data.size() > 0) {
			throw new BizException("图片名称不能重复");
		}
		pd.fillUpdatedData();
		daoSupport.update("AppMenuConfigMapper.update", pd);
	}
	
	/**
	 * @param {"imgName":"aaa"}
	 */
	private PageData findByName(PageData pd) {
		return daoSupport.findForPageData("AppMenuConfigMapper.findByName", pd);
	}
	
}
