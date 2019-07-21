package com.imooc.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;

/**
 * 类描述： ShopService接口的实现类
 * 需要考虑接口和实现类是怎么关联上的，如果有多个实现类在controller自动装配的时候是怎么注入的。
 * 实现类自动注入了shopdao进行数据库持久化操作
 * @author zhangzhl
 *
 */
@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream ,String fileName) {
		// 空值判断
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO); 
			
		}
		try {
			// 给店铺信息赋初始值
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum < 0) {
				throw new ShopOperationException("店铺创建失败 ");
			} else {
				if (shopImgInputStream != null) {
					try {
						// 存储图片
						addShopImg(shop, shopImgInputStream,fileName);
					} catch (Exception e) {
						throw new ShopOperationException("addshopImg  error :" + e.getMessage());
					}
					// 更新商铺的图片地址信息
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
				
			}
		} catch (Exception e) {
			throw new ShopOperationException("addshop error :" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	/**
	 * @param shop
	 * @param shopImg
	 */
	private void addShopImg(Shop shop, InputStream shopImgInputStream ,String fileName) {
		// 获取目标图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, dest,fileName);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shodId) {
		
		return shopDao.queryByShopId(shodId);
	}

	/**
	 * 方法描述：店铺信息修改
	 * 两 步：1、店铺的图片信息修改
	 * 		 2、店铺的其他内容修改
	 */
	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) {
		if(shop == null||shop.getShopId()==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		} else {
			try {
				//1、判断是否需要处理图片
				if(shopImgInputStream !=null && fileName!=null && "".equals(fileName)){
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					// 先将店铺的历史图片移除
					if(tempShop.getShopImg()!=null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					// 将新的图片添加到店铺信息上
					addShopImg(shop,shopImgInputStream,fileName);
				}
				// 2、更新店铺信息
				shop.setLastEditTime(new Date());
				int effctedNum = shopDao.updateShop(shop);
				if(effctedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS,shop);
				}
			} catch (Exception e) {
				throw new ShopOperationException("modifyShop error" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * 方法描述：获取店铺列表
	 */
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// 前端只显示页数，后端数据库存的是行数所以需要进行转换
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if(shopList != null) {
			se.setShoplist(shopList);
			se.setCount(count);
		} else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
