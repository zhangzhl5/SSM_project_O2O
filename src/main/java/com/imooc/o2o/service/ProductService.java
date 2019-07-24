package com.imooc. o2o.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;

/**
 * 商品接口
 * @author zhangzhl
 *
 */
public interface ProductService {
	
	// 查询商品列表
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	// 根据商品ID查询商品
	Product getProductById(long productId);

	// 添加商品
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
			throws RuntimeException;
	// 修改商品信息
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs) throws RuntimeException;
	
}
