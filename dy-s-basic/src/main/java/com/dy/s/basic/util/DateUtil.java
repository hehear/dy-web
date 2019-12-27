package com.dy.s.basic.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author dxy
 * @date 20191203
 *
 */
public class DateUtil {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * 格式"YYYY-MM-DD"
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateString(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd");

	}

	/**
	 * 按照指定格式，格式化日期
	 * 
	 * @param date
	 * @param pattern
	 *            格式化字符串
	 * @return String
	 */
	public static String getDateString(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);

	}

	/**
	 * 获取当前日期
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 获取mongodb的当前时间，和java获取的差8小时
	 * 
	 * @return Date
	 *
	 * @author dxy
	 * @date 20191203
	 */
	public static Date getCurrentDate4Mongo(){
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 8);
		return c.getTime();
		
	}

	/**
	 * 获取当前时间
	 */
	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

}
