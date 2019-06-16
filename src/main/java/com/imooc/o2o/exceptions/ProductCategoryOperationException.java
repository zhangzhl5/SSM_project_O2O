package com.imooc.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException {

	/**
	 * 支持序列化
	 */
	private static final long serialVersionUID = 1182563719599527969L;

	public ProductCategoryOperationException(String msg){
		super(msg);
	}

}
