package com.ztessc.einvoice.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ztessc.einvoice.exception.BizException;

public class DateUtil {
	
	/**日期格式：yyyy-MM-dd**/
	public final static ThreadLocal<DateFormat> sdfDate = new ThreadLocal<DateFormat>(){
		@Override
		protected DateFormat initialValue(){
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	/**日期格式：yyyy-MM-dd HH:mm:ss**/
	public final static ThreadLocal<DateFormat> sdfDateTime = new ThreadLocal<DateFormat>(){
		@Override
		protected DateFormat initialValue(){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	/**
	 * 获取当前时间
	 * @author: 徐益森
	 * @date: 2018年4月10日 上午10:30:26
	 * @return Date
	 */
	public static Date getCurrentDateTime() {
		return new Date();
	}
	
	public static String getDate(Date date) {
		return sdfDate.get().format(date);
	}
	
	public static String getDateTime(Date date) {
		return sdfDateTime.get().format(date);
	}
	
	public static Date fomatDateYmd(String date) {
		try {
			return sdfDate.get().parse(date);
		} catch (ParseException e) {
			throw new BizException("操作失败，日期校验不通过", e);
		}
	}
	
	public static Date fomatDateYmdhms(String date) {
		try {
			return sdfDateTime.get().parse(date);
		} catch (ParseException e) {
			throw new BizException("操作失败，日期校验不通过", e);
		}
	}
	
}
