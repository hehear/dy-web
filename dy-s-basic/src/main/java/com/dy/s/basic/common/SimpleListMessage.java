package com.dy.s.basic.common;

import java.util.List;

/**
 * 带有list数据的simpleMessage
 *
 * @author dxy
 * @date 20191203
 *
 * @param <T>
 */
public class SimpleListMessage<T> extends SimpleMessage<T> {
	/**
	 * 返回列表实体结果信息
	 */
	private List<T> records;

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}
}
