package com.ztessc.einvoice.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * 基于jwt的token验证
 * https://blog.csdn.net/weixin_38568779/article/details/76833848
 * Copyright © 2018 zte. All rights reserved.
 * @Description: TODO
 * @Title: JWTUtil.java 
 * @Prject: ztessc-einvoice
 * @Package: com.ztessc.einvoice.util 
 * @ClassName: JWTUtil 
 * @author: 徐益森   
 * @date: 2018年5月21日 上午11:38:55 
 * @version: V1.0.0
 */
public class JwtUtil {

	//token 过期时间5分钟
    private static final long EXPIRE_TIME = 60 * 1000;
    
    //免登录时间为 7 天，由于app一旦登录，之后就不应该再进行登录，所以只要在此时间之内需要刷新token
    private static final long REMEMBER_TIME = 7 * 24 * 60 * 60 * 1000;

//    //密钥为 userId + ZTESSC 组合
//	private static final String SUFFIX_SECRET = "_ZTESSC";

	//私有秘钥secretKey（可以对其base64加密）
    private static final String SECRETKET = "eHh4eGJiYmNjY2RkZGVlZWZmZmRkZA==";
    
	/**
     * 生成签名,5min后过期
     * @author: 徐益森
     * @date: 2018年5月21日 下午1:46:31
     * @param {"id":"aaa","userName":"张三","accountNo":"admin"}
     * @return 加密的token
     */
    public static String sign(PageData user) {
        try {
        	//jwt的签发时间
            Date iatTime = new Date();
            //jwt的过期时间，这个过期时间必须要大于签发时间
            Date expTime = new Date(iatTime.getTime() + EXPIRE_TIME);
            //免登录时间
            Date rememberTime = new Date(iatTime.getTime() + REMEMBER_TIME);
            
//            String secret = getSecret(user.getString("id"));
            Algorithm algorithm = Algorithm.HMAC256(SECRETKET);
            return JWT.create()
                    .withClaim("userId", user.getString("id"))
                    .withClaim("userName", user.getString("userName"))
                    .withClaim("accountNo", user.getString("accountNo"))
                    .withClaim("rememberTime", rememberTime)
                    .withIssuer("ztessc")
                    .withNotBefore(iatTime)
                    .withIssuedAt(iatTime)
                    .withExpiresAt(expTime)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 校验token是否正确
     * @author: 徐益森
     * @date: 2018年5月21日 下午1:48:21
     * @param token token
     * @param secret 密钥
     * @return 是否正确，0=正确，1=token过期，2=错误
     */
    public static int verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRETKET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return 0;
        } catch (TokenExpiredException e) {
            return 1;
        } catch (Exception e) {
        	e.printStackTrace();
            return 2;
        }
    }
    
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    
    public static String getAccountNo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("accountNo").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    
    public static long getExpiresTime(String token) {
    	DecodedJWT jwt = JWT.decode(token);
    	return jwt.getExpiresAt().getTime();
    }
    
    public static PageData getUserInfo(String token) {
    	DecodedJWT jwt = JWT.decode(token);
    	
    	PageData payload = PageData.parseJson(Base64Util.decode(jwt.getPayload()));
    	
    	PageData user = new PageData();
    	user.put("id", payload.getString("userId"));
    	user.put("userName", payload.getString("userName"));
    	user.put("accountNo", payload.getString("accountNo"));
    	
    	return user;
    }
    
    /**
     * 刷新token
     * @author: 徐益森
     * @date: 2018年5月21日 下午3:30:54
     * @param {}
     * @return String
     */
    public static String refreshToken(String token) {
    	return sign(getUserInfo(token));
    }
    
//    /**
//     * 根据userId生成密钥
//     * @author: 徐益森
//     * @date: 2018年5月21日 下午3:01:34
//     * @param {}
//     * @return String
//     */
//    public static String getSecret(String userId) {
//    	return userId + SUFFIX_SECRET;
//    }
    
    /**
     * 过期时长(毫秒)
     * @author: 徐益森
     * @date: 2018年6月1日 上午10:00:01
     * @param {}
     * @return long
     */
    public static long getExpireTime() {
    	return EXPIRE_TIME;
    }
    
    public static void main(String[] args) {
    	PageData user = new PageData();
    	user.put("id", "aaa");
    	user.put("userName", "张三");
		String token = sign(user);
System.out.println(token);
//		
//		int bool = verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjY4ODM2OTgsInVzZXJJZCI6ImFhYSIsImlhdCI6MTUyNjg4MzY5MywicmVtZW1iZXJUaW1lIjoxNTI3NDg4NDkzfQ.PPz-yLNOjiJgdABth5A7R3Z3Vo_AhOUi8zu8gEzI3JQ", "aaa_ZTESSC");
//System.out.println(bool);
		
		System.out.println(Base64Util.decode("eyJhbGciOiJIUzI1NiJ9"));
		System.out.println(Base64Util.decode("eyJ1c2VySWQiOiJhYWEiLCJ1c2VyTmFtZSI6IuW8oOS4iSIsInJlbWVtYmVyVGltZSI6MTUyNjk3ODg5MDkzOSwiaWF0IjoxNTI2OTc4ODMwLCJleHAiOjE1MjY5Nzg4NjB9"));
		System.out.println(Base64Util.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"));
		System.out.println(Base64Util.decode("eyJleHAiOjE1MjY5Nzg4NzgsInVzZXJJZCI6ImFhYSIsInVzZXJOYW1lIjoi5byg5LiJIiwiaWF0IjoxNTI2OTc4ODQ4LCJyZW1lbWJlclRpbWUiOjE1MjY5Nzg5MDh9"));
		
	}
}
