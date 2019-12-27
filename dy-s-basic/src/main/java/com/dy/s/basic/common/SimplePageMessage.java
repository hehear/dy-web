package com.dy.s.basic.common;

/**
 * 相应统一返回数据定义(分页)
 *
 * @author dxy
 * @date 20191203
 *
 */
public class SimplePageMessage<T> extends SimpleMessage<T> {
	 
	/**
	 * 分页信息
	 */
	private Pager<T> pager;

	public Pager<T> getPager() {
		return pager;
	}

	public void setPager(Pager<T> pager) {
		this.pager = pager;
	}
	
	
	
}
