package com.ztessc.einvoice.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.cache.AppSingleDeviceLoginCache;
import com.ztessc.einvoice.cache.LogoutCache;
import com.ztessc.einvoice.controller.BaseController;
import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.service.AppBootConfigService;
import com.ztessc.einvoice.service.AppButtonConfigService;
import com.ztessc.einvoice.service.AppLogoConfigService;
import com.ztessc.einvoice.service.AppMenuConfigService;
import com.ztessc.einvoice.service.AppService;
import com.ztessc.einvoice.service.AppSlideshowConfigService;
import com.ztessc.einvoice.service.UserService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.JwtUtil;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.MD5;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

/**
 * 
 * Copyright © 2018 zte. All rights reserved.
 * @Description: TODO
 * @Title: AppController.java 
 * @Prject: pl
 * @Package: com.zte.pl.controller 
 * @ClassName: AppController 
 * @author: 徐益森   
 * @date: 2018年4月16日 下午3:55:11 
 * @version: V1.0.0
 */
@RequestMapping(value = "/app")
@RestController
public class AppController extends BaseController {

	private static final Logger log = Log.get();
	
	@Autowired
	private AppBootConfigService appBootConfigService;
	@Autowired
	private AppSlideshowConfigService appSlideshowConfigService;
	@Autowired
	private AppLogoConfigService appcConfigService;
	@Autowired
	private AppButtonConfigService appButtonConfigService;
	@Autowired
	private AppMenuConfigService appMenuConfigService;
	@Autowired
	private UserService userService;
	@Autowired
	private AppService appService;
	@Autowired
	private LogoutCache logoutCache;
	@Autowired
	private AppSingleDeviceLoginCache appSingleDeviceLoginCache;
	
//	@Autowired
//	private AppUserCache appUserCache;
	
	@PostMapping(value="/v1/init/home")
	public PageData index() {
		//获取app启动页
		PageData bootConfig = appBootConfigService.findBootConfig(null);
//		//获取app轮播图
//		List<PageData> listSlideshows = appSlideshowConfigService.listSlideshows();
//		//获取app logo
//		PageData logoConfig = appcConfigService.findLogoConfig(null);
//		//获取app栏目
//		List<PageData> listButtons = appButtonConfigService.listButtons();
//		//获取app菜单
//		List<PageData> listMenus = appMenuConfigService.listMenus();
		
		PageData result = new PageData();
		result.put("bootConfig", bootConfig);
//		result.put("listSlideshows", listSlideshows);
//		result.put("logoConfig", logoConfig);
//		result.put("listButtons", listButtons);
//		result.put("listMenus", listMenus);
		return MessageUtil.getSuccessMessage(result);
	}
	
	/**
     * 登录方法
     * @param {"username":"admin","password":"888888"}
     * @return
     */
	@PostMapping(value = "/v1/init/login")
    public PageData login(HttpServletRequest request, HttpServletResponse response) {
		PageData params = this.getPageData();
        PageData result = new PageData();
    	PageData user = userService.findBy(params);
    	if(user == null || !user.getString("password").equals(MD5.md5(params.getString("password")))) {
    		throw new BizException("用户名或密码错误", Const.ERROR_CODE_USERNAME_PASSWORD_ERROR);
    	}
    	
        result.put("user", user);
        String token = JwtUtil.sign(user);
    	result.put(Const.JWT_TOKEN_NAME, token);
        
        request.setAttribute(Const.SESSION_LOGIN_USER, user);
        
        appSingleDeviceLoginCache.cache(user.getString("id"), request.getHeader(Const.HEADER_DEVICE_TOKEN));
        
        return MessageUtil.getSuccessMessage(result, "登录成功");
    }
    
    /**
	 * 用户注销
	 */
	@RequestMapping(value = "/v1/init/logout")
	public PageData logout(HttpServletRequest request) {
		try {
			String token = request.getHeader(Const.JWT_TOKEN_NAME);
			long expiresTime = JwtUtil.getExpiresTime(token);
			long currentTime = System.currentTimeMillis();
			
			logoutCache.cache(token, (expiresTime - currentTime) / 1000);
		} catch (Exception e) {
			log.error("注销缓存失败", e);
		}
		
		return MessageUtil.getSuccessMessage("注销成功");
	}
	
	/**
	 * app版本检查，判断某个版本是否要强制更新
	 * @author: 徐益森
	 * @date: 2018年5月10日 上午10:06:12
	 * @param {"version":"1.0.0","platform":"android/ios"}
	 * @return PageData
	 */
	@PostMapping(value = "/v1/init/versionCheck")
	public PageData versionCheck() {
		PageData params = this.getPageData();
		PageData result = appService.versionCheck(params);
		return MessageUtil.getSuccessMessage(result);
	}
}
