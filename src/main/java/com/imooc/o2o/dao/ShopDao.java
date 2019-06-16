package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	
	/**
	 * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域ID，owner
	 * @param ShopCondition
	 * @param rowIndex 从第几行开始取数据
	 * @param pagesize 返回的行数
	 * @return
	 */
	List<Shop> queryShopList(@Param("ShopCondition") Shop ShopCondition,@Param("rowIndex") int rowIndex,
			@Param("pagesize") int pagesize);
	
	int queryShopCount(@Param("ShopCondition") Shop ShopCondition);
	/**
	 * 新增店铺 
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺信息
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	
	/**
	 * 根据店铺ID查询店铺信息
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);

}
