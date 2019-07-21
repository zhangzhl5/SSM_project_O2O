package com.imooc.o2o.service;

import java.util.List;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;

/**
 * 类描述：商品种类接口
 * @author zhangzhl
 *
 */
public interface ProductCategoryService {
	
	/**
	 * 方法描述：查询🈯定店铺下的所有商品种类信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
	
	/**
	 * 方法描述：批量添加商品种类
	 * @param productCategoryList
	 * @return
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) 
			throws ProductCategoryOperationException;
	
	/**
	 * 方法描述：删除指定的商品种类
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) 
			throws ProductCategoryOperationException;

}
