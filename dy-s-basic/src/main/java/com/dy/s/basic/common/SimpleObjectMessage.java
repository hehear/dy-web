package com.dy.s.basic.common;

/**
 * 结果数据为单个实体的SimpleMessage
 *
 * @author dxy
 * @date 20191203
 * 
 * @param <T>
 */
public class SimpleObjectMessage<T> extends SimpleMessage<T> {
	/**
	 * 返回单个实体结果
	 */
	private T record;

	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}
	
}
