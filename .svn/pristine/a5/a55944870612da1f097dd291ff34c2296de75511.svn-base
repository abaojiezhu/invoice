package com.ztessc.einvoice.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * Copyright © 2018 zte. All rights reserved.
 * @Description: TODO
 * @Title: AppSsoCache.java 
 * @Prject: ztessc-einvoice
 * @Package: com.ztessc.einvoice.cache 
 * @ClassName: AppSsoCache 
 * @author: 徐益森   
 * @date: 2018年6月1日 下午5:52:46 
 * @version: V1.0.0
 */
@Component
public class AppSingleDeviceLoginCache {

	private String APP_USER_PREFIX = "APP_USER_";
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	public void cache(String key, String value) {
		stringRedisTemplate.opsForValue().set(APP_USER_PREFIX + key, value);
	}
	
	public boolean exist(String key) {
		return stringRedisTemplate.hasKey(APP_USER_PREFIX + key);
	}
	
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(APP_USER_PREFIX + key);
	}
	
}
