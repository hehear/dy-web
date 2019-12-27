package com.dy.s.basic.common;

/**
 * 异常时返回的simpleMessage
 * 
 * @author dxy
 * @date 20191203
 *
 * @param <T>
 */
public class SimpleErrorMessage<T> extends SimpleMessage<T> {
	
	/**
	 * 堆栈信息
	 */
	private StackTraceElement[] stackTrace;

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	} 
	
	
}
