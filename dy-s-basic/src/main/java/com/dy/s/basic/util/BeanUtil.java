package com.dy.s.basic.util;

import com.baidu.unbiz.easymapper.Mapper;
import com.baidu.unbiz.easymapper.MapperFactory;

/**
 * bean工具类
 *
 * @author dxy
 * @date 20191203
 *
 */
public class BeanUtil  {
	
	private static Mapper MAPPER=MapperFactory.getCopyByRefMapper();
	
	/**
	 * 对象copy
	 * 
	 * @param orig 源头对象
	 * @param target 目标对象
	 * @return 一个新的目标对象target
	 */
	public static <T> T copy(Object orig,Class<T> target) {
		
		return (T)MAPPER.mapClass(orig.getClass(), target).registerAndMap(orig, target);

	} 
	
	/**
	 * 对象copy
	 * 
	 * @param orig 源头对象
	 * @param target 目标对象
	 * @param ignoreFields 忽略copy的字段
	 * @return 一个新的目标对象target
	 *
	 * @author dxy
	 * @date 20191203
	 */
	public static <T> T copy(Object orig,Class<T> target,String[] ignoreFields) {
		
		return (T)MAPPER.mapClass(orig.getClass(), target).exclude(ignoreFields).registerAndMap(orig, target);
		
	} 
	
	 
}
