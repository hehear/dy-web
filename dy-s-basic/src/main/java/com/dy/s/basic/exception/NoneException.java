package com.dy.s.basic.exception;


import com.dy.s.basic.enums.MessageEnum;

/**
 * 无资源异常404
 *
 * @author dxy
 * @date 20191203
 *
 */
public class NoneException extends AbstractBaseExcepton{ 
	 
	private static final long serialVersionUID = 1L;
	
	{ 
		this.code= MessageEnum.NONE.getCode();
	}

	public NoneException(){
		super();
	}
	
	public NoneException(String message){
	   super(message);	
	}
	
	public NoneException(Throwable err){
		super(err);
	}
	
	public NoneException(String message,Throwable err){
		super(message,err);
	}

}
