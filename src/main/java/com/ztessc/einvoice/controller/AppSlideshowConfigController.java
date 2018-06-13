package com.ztessc.einvoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.service.AppSlideshowConfigService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RequestMapping(value = "/config/slideshow")
@RestController
public class AppSlideshowConfigController extends BaseController {

	@Autowired
	private AppSlideshowConfigService appSlideshowConfigService;
	
	/**
	 * 获取所有数据
	 * @author: 徐益森
	 * @date: 2018年4月11日 下午6:19:59
	 * @return PageData
	 */
	@PostMapping(value = "/list")
    public PageData list() {
    	List<PageData> list = appSlideshowConfigService.listSlideshowConfigs();
        return MessageUtil.getSuccessMessage(list);
    }
	
    /**
     * 新增
     * @author: 徐益森
     * @date: 2018年4月11日 下午6:22:26
     * @param {"imgName":"票联系统","imgLink":"www.baidu.com","fileName":"xxxx.png","realName":"xxxx.png","enabled":"Z001001","sort":""}
     * @return PageData
     */
	@PostMapping(value = "/save")
	public PageData save() {
		PageData params = this.getPageData();
		appSlideshowConfigService.save(params);
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_SAVE);
	}
	
	/**
     * 修改
     * @author: 徐益森
     * @date: 2018年4月11日 下午6:22:26
     * @param {"id":"aaa","imgName":"票联系统","imgLink":"www.baidu.com","fileName":"xxxx.png","realName":"xxxx.png","enabled":"Z001002","sort":""}
     * @return PageData
     */
	@PostMapping(value = "/update")
	public PageData update() {
		PageData params = this.getPageData();
		appSlideshowConfigService.update(params);
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_UPDATE);
	}
	
	/**
     * 删除
     * @author: 徐益森
     * @date: 2018年4月11日 下午6:22:26
     * @param {"id":"aaa"}
     * @return PageData
     */
	@PostMapping(value = "/delete")
	public PageData delete() {
		PageData params = this.getPageData();
		appSlideshowConfigService.delete(params);
		return MessageUtil.getSuccessMessage(Const.MESSAGE_SUCCESS_DELETE);
	}
	
}
