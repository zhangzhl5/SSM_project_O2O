 package com.imooc.o2o.util;

/**
 * 前端页数的转换方法
 * @author zhangzhl
 *
 */
public class PageCalculator {
	
	/**
	 * 方法描述：将pageIndex转换成rowindex
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		
		return (pageIndex > 0) ? (pageIndex - 1)*pageSize : 0 ;
		
	}

}
