package com.dy.s.basic.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串相关工具类
 * 
 * @des 基于org.apache.commons.lang3.StringUtils
 *
 * @author dxy
 * @date 20191203
 *
 */
public class StringUtil {
	
	public static boolean isEmpty(String str){
		return str==null || str=="" || str.trim()=="" ; 
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	public static boolean isNumber(String str){
		if(isEmpty(str)){
		   return false;	
		}
		return StringUtils.isNumeric(str);
	}
	
	public static boolean isNotNumber(String str){
		 
		return !isNumber(str);
	}

}
