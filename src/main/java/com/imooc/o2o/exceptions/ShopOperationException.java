package com.imooc.o2o.exceptions;

public class ShopOperationException extends RuntimeException {

	/**
	 * 支持序列化
	 */
	private static final long serialVersionUID = -6202858922213755675L;
	
	public ShopOperationException(String msg){
		super(msg);
	}

}
