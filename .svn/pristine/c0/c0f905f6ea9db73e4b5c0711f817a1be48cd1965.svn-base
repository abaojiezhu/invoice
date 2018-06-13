package com.ztessc.einvoice.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
	
    private static Logger logger = Log.get();

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), status);
    }

    @ExceptionHandler(value = Exception.class)
    public PageData exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
    	//记录当前用户操作异常，用于日志记录
		request.setAttribute(Const.HEADER_ERROR, e);
    			
    	log(e, request);

    	String message = e.getMessage();
    	
    	//未授权
    	if(e instanceof UnauthorizedException) {
    		if(StringUtils.isNotBlank(message) && message.indexOf("Subject does not have permission") != -1) {
        		message = getPermissionMessage(message);
//        		error.put("code", "1000001");
        		return MessageUtil.getErrorMessage(message, Const.ERROR_CODE_UNAUTH);
        	}
    	}
    	
    	//帐号已失效
    	if(e instanceof BizException) {
    		BizException bizException = (BizException) e;
    		if(Const.ERROR_CODE_UNLOGIN.equals(bizException.getErrorCode())) {
        		return MessageUtil.getErrorMessage(message, Const.ERROR_CODE_UNLOGIN);
        	}
    	}
    	
        return MessageUtil.getErrorMessage(message);
    }

    private String getPermissionMessage(String message) {
    	try {
    		return message.replace("Subject does not have permission", "没有权限");
		} catch (Exception e) {
			logger.error("", e);
		}
    	return message;
    }
    
    private void log(Exception ex, HttpServletRequest request) {
        logger.error("************************异常开始*******************************");
        logger.error("请求地址：" + request.getRequestURL());
        logger.error("请求参数：" + new PageData(request));
        logger.error("异常堆栈信息:", ex);
        logger.error("************************异常结束*******************************");
    }
}