package com.ztessc.einvoice.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	
	    /**
	     * 获得Logger
	     * @param clazz 日志发出的类
	     * @return Logger
	     */
	    public static Logger get(Class<?> clazz) {
	        return LoggerFactory.getLogger(clazz);
	    }
	 
	    /**
	     * 获得Logger
	     * @param name 自定义的日志发出者名称
	     * @return Logger
	     */
	    public static Logger get(String name) {
	        return LoggerFactory.getLogger(name);
	    }
	     
	    /**
	     * @return 获得日志，自动判定日志发出者
	     */
	    public static Logger get() {
	        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        return LoggerFactory.getLogger(stackTrace[2].getClassName());
	    }
	     
}
