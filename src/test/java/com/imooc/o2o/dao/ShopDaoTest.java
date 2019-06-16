package com.imooc.o2o.dao;


import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest  extends BaseTest{
	
	@Autowired
	private ShopDao shopDao;
	
	

	@Test
	@Ignore
	public void testqueryShopListAndCount() {
		Shop shopCondition  = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shoplist = shopDao.queryShopList(shopCondition, 0, 5);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("列表大小" + shoplist.size());
		System.out.println("count" + count );
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		shoplist = shopDao.queryShopList(shopCondition, 0, 2);
		count = shopDao.queryShopCount(shopCondition);
		System.out.println("dd列表大小" + shoplist.size());
		System.out.println("xcount" + count );
		
	}
	@Test
	@Ignore
	public void testQueryByShopId () {
		long shopId = 2;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getArea().getAreaName());
	}
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试店铺");
		shop.setShopDesc("test ");
		shop.setShopAddr("test");
		shop.setPhone("2344");
		shop.setShopImg("sdf");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中 ");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(2L);
		shop.setShopDesc("skk ");
		shop.setShopAddr("sfsg");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
	

}
