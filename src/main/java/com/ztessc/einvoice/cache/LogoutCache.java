package com.ztessc.einvoice.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 由于退出登录jwt token 并不能立即失效，所以此处做法是把此jwt token放入缓存中
 * Copyright © 2018 zte. All rights reserved.
 * @Description: TODO
 * @Title: LogoutCache.java 
 * @Prject: ztessc-einvoice
 * @Package: com.ztessc.einvoice.cache 
 * @ClassName: LogoutCache 
 * @author: 徐益森   
 * @date: 2018年5月28日 下午4:06:07 
 * @version: V1.0.0
 */
@Component
public class LogoutCache {

	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	public void cache(String key, long expires) {
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		if(expires < 0) {
			expires = 1;
		}
		opsForValue.set(key, "", expires, TimeUnit.SECONDS);
	}

	public boolean exist(String key) {
		return stringRedisTemplate.hasKey(key);
	}
	
}
