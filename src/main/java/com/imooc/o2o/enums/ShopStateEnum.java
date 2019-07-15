package com.imooc.o2o.enums;

/*
 * 店铺状态枚举
 */
/**
 * @author zhangzhl
 *
 */
public enum ShopStateEnum {

	CHECK(0, "审核中"), 
	OFFLINE(-1, "非法商铺"),
	SUCCESS(1, "操作成功"),
	PASS(2, "通过认证"),
	INNER_ERROR(-1001, "操作失败"),
	NULL_SHOPID(-1002, "ShopId为空"), 
	NULL_SHOP_INFO(-1003, "传入了空的信息");

	private int state;

	private String stateInfo;

	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	
	/**
	 * 方法描述： 根据传入的state返回相应的enum值
	 * @param index
	 * @return
	 */
	public static ShopStateEnum stateOf(int index) {
		for (ShopStateEnum state : ShopStateEnum.values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
