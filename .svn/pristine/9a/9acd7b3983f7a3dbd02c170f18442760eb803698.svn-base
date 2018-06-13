package com.ztessc.einvoice.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ztessc.einvoice.cache.JwtCache;
import com.ztessc.einvoice.cache.LogoutCache;
import com.ztessc.einvoice.service.LogService;
import com.ztessc.einvoice.shiro.JwtToken;
import com.ztessc.einvoice.util.ApplicationContextUtil;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.JwtUtil;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

public class JwtFilter extends AccessControlFilter {

	private static final Logger log = Log.get();
	
	//需要放行的url
	private String[] excludePaths = new String[]{"/init/login","/init/logout"};
	
    private LogService logService;
    private JwtCache jwtCache;
    private LogoutCache logoutCache;
	
	
	/**
	 * 判断当前url是否需要放行
	 */
    private boolean include(ServletRequest request, ServletResponse response) {
    	HttpServletRequest req = (HttpServletRequest) request;
    	String url = req.getRequestURI();
        for (String exclude : excludePaths) {
			if (url.endsWith(exclude)) {
				return true;
			}
		}
		return false;
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        //获取token得到jwt格式的token信息
        String authorization = httpServletRequest.getHeader("Authorization");
        //除了需要放行的url，任何url都必须携带Authorization头信息，以便进行身份验证，否则就是非法请求
        if(StringUtils.isNotBlank(authorization)) {
        	//验证jwt有效性(0=正确，1=token过期，2=错误)
        	int verify = JwtUtil.verify(authorization);
        	if(verify == 0) {
        		boolean exist = getLogoutCache().exist(authorization);
        		if(exist) {
        			writeMsg(response, MessageUtil.getErrorMessage("帐号已失效，请重新登录",Const.ERROR_CODE_UNLOGIN).toString());
        			return false;
        		}
    			
        		try {
        			JwtToken token = new JwtToken(authorization);
        			// 提交给realm进行登入，如果错误他会抛出异常并被捕获
        			getSubject(request, response).login(token);
        		} catch (Exception e) {
        			log.error(e.getMessage(), e);
        			writeMsg(response, MessageUtil.getErrorMessage(e.getMessage()).toString());
        			return false;
        		}
        		return true;
        	}else if(verify == 1) {
        		synchronized (JwtFilter.class) {
        			//处理并发情况,旧token缓存30秒，30秒之内放行
        			if(getJwtCache().exist(authorization)) {
//        				httpServletResponse.setHeader(Const.JWT_TOKEN_NAME, getJwtCache().get(authorization));
        				return true;
        			}
        			
        			//是否在黑名单内
        			if(getJwtCache().existBlackList(authorization)) {
        				writeMsg(response, MessageUtil.getErrorMessage("帐号已失效，请重新登录",Const.ERROR_CODE_UNLOGIN).toString());
        				return false;
        			}
        			
        			/*
        			 * token过期刷新token
        			 * 针对app端：当前时间 > 过期时间 && <= 免登录时间，则重新生成jwt
        			 * 针对web端：由于没有免登录时间，所以根据最后访问时间判断，当前时间 > 过期时间 && <= 最后访问时间+过期时长，则重新生成jwt
        			 */
        			DecodedJWT jwt = JWT.decode(authorization);
        			//当前时间
        			long currTime = System.currentTimeMillis();
        			//过期时间
        			long expTime = jwt.getExpiresAt().getTime();
        			
        			if(httpServletRequest.getRequestURI().startsWith("/app/")) {
        				//免登录时间(针对app端有用，web端无效)
        				long rememberTime = jwt.getClaim("rememberTime").asDate().getTime();
        				//如果当前时间 > 过期时间 && <= 免登录时间，则重新生成jwt
        				if(currTime > expTime && currTime <= rememberTime) {
        					//返回给客户端刷新的token
        					String newToken = JwtUtil.refreshToken(authorization);
        					httpServletResponse.setHeader(Const.JWT_TOKEN_NAME, newToken);
        					getJwtCache().cache(authorization, newToken);
        					return true;
        				}
        			}else {
        				//最后访问时间,查询日志表
        				String userId = jwt.getClaim("userId").asString();
        				PageData pd = new PageData();
        				pd.put("userId", userId);
        				PageData log = getLogService().findLastTimeByUser(pd);
        				
        				//最后访问时间
        				long lastTime = ((Date)log.get("beginTime")).getTime();
        				//jwt token 过期时间
        				long expireTime = JwtUtil.getExpireTime();
        				
        				if(currTime > expTime && currTime <= lastTime + expireTime) {
        					//返回给客户端刷新的token
        					String newToken = JwtUtil.refreshToken(authorization);
        					httpServletResponse.setHeader(Const.JWT_TOKEN_NAME, newToken);
        					
        					getJwtCache().cache(authorization, newToken);
        					
        					return true;
        				}
        			}
				}
    			writeMsg(response, MessageUtil.getErrorMessage("帐号已失效，请重新登录",Const.ERROR_CODE_UNLOGIN).toString());
    			return false;
        	}else {
    			writeMsg(response, MessageUtil.getErrorMessage("jwt token 验证失败").toString());
    			return false;
        	}
        }
        
		writeMsg(response, MessageUtil.getErrorMessage("非法请求！").toString());
        return false;
    }

    /**
     * 先执行：isAccessAllowed 再执行onAccessDenied
     *
     * isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，
     * 如果允许访问返回true，否则false；
     *
     * 如果返回true的话，就直接返回交给下一个filter进行处理。
     * 如果返回false的话，回往下执行onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return include(request, response);
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");	//nginx中已经设置过了，此处不需要在设置
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

	private void writeMsg(ServletResponse response, String msg) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().append(msg);
	}

	public LogService getLogService() {
		if(logService == null) {
			return (LogService) ApplicationContextUtil.getBean("logService");
		}
		return logService;
	}

	public JwtCache getJwtCache() {
		if(jwtCache == null) {
			return (JwtCache) ApplicationContextUtil.getBean("jwtCache");
		}
		return jwtCache;
	}

	public LogoutCache getLogoutCache() {
		if(logoutCache == null) {
			return (LogoutCache) ApplicationContextUtil.getBean("logoutCache");
		}
		return logoutCache;
	}
    
}
