package com.ztessc.einvoice.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.ztessc.einvoice.util.JwtUtil;

/**
 * jwt token 缓存
 * 主要用于并发请求。也就是2个小时候之后，同一个页面发来了2个请求，这个很正常，比如一个请求列表数据，一个请求搜索的表单，因为token都已失效，那么难道返回2个新的token回去？
 * jwt肯定不会发两个新的token回去的，那麽肯定会有一个token不仅是失效了，刷新当前token之后，产生新的token，旧token加入到了backlist中了，无法使用，
 * 那么另外一个请求自然无法成功。我这里使用Redis解决的，将旧token作为键，新token作为值，设置一个30秒过期的时间。当第二个请求来的时候，已经知道token在backlist中了，
 * 我们可以去redis查询下是否存在这么个旧token，存在的话放行。
 * Copyright © 2018 zte. All rights reserved.
 * @Description: TODO
 * @Title: JWTCache.java 
 * @Prject: ztessc-einvoice
 * @Package: com.ztessc.einvoice.cache 
 * @ClassName: JwtCache 
 * @author: 徐益森   
 * @date: 2018年5月28日 上午10:54:00 
 * @version: V1.0.0
 */
@Component
public class JwtCache {

	private String JWT_BLACK_PREFIX = "DISABLE_JWT_";
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	public void cache(String key, String value) {
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.set(key, value, 30, TimeUnit.SECONDS);
		opsForValue.set(JWT_BLACK_PREFIX + key, value, JwtUtil.getExpireTime()/1000, TimeUnit.SECONDS);
	}
	
	public boolean exist(String key) {
		return stringRedisTemplate.hasKey(key);
	}
	
	public boolean existBlackList(String key) {
		return stringRedisTemplate.hasKey(JWT_BLACK_PREFIX + key);
	}
	
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}
	
}
