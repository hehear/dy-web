package com.dy.s.basic.exception;

import com.dy.s.basic.enums.MessageEnum;

/**
 * 无权限异常
 *
 * @author dxy
 * @date 20191203
 *
 */
public class AuthException extends AbstractBaseExcepton{ 
	 
	private static final long serialVersionUID = 1L;
	
	{ 
		this.code= MessageEnum.AUTHORITY.getCode();
	}
	
	public AuthException(){
		super();
	}
	
	public AuthException(String message){
	   super(message);	
	}
	
	public AuthException(Throwable err){
		super(err);
	}
	
	public AuthException(String message,Throwable err){
		super(message,err);
	}

}
