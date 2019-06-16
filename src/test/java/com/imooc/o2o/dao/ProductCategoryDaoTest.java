package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void testBQueryByShopId() {
		long shopId = 2;
		List<ProductCategory>  productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		
		System.out.println("该店铺自定义类别数为" + productCategoryList.size());
	}

	@Test
	public void testABatchInsertProductCategory() {
		ProductCategory productCategory  = new ProductCategory();
		productCategory.setProductCategoryName("商品类别四");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(2l);
		List<ProductCategory> proList = new ArrayList<ProductCategory>();
		proList.add(productCategory);
		int effNum = productCategoryDao.batchInsertProductCategory(proList);
		assertEquals(1, effNum);
	}
	@Test
	public void testCDeleteProductCategory() {
		long shopId = 2l;
		List<ProductCategory>  productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		for(ProductCategory  pro :productCategoryList) {
			if("商品类别四".equals(pro.getProductCategoryName())) {
				int effNum = productCategoryDao.deleteProductCategory(pro.getProductCategoryId(), shopId);
				assertEquals(1, effNum);
			}
		}
	}
}
