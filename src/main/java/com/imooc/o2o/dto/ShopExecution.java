package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;

public class ShopExecution {
	// 店铺状态
	private int state;
	// 状态标示
	private String stateInfo;
	// 店铺数量
	private int count;
	// 操作的店铺
	private Shop shop;
	// 店铺列表
	private List<Shop> shoplist;
	
	public ShopExecution() {
		
	}
	// 店铺操作失败时用到的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo() ;
	}
	// 店铺操作成功时用到的构造器
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo() ;
		this.shop = shop;
	}
	// 店铺操作成功时用到的构造器
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shoplist) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo() ;
		this.shoplist = shoplist;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShoplist() {
		return shoplist;
	}
	public void setShoplist(List<Shop> shoplist) {
		this.shoplist = shoplist;
	}

}
