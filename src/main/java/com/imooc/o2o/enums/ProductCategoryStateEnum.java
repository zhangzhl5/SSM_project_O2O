package com.imooc.o2o.enums;

/**
 * 产品类别结果状态枚举
 * @author zhangzhl
 *
 */
public enum ProductCategoryStateEnum {
	// 状态标示符
	SUCCESS(1, "创建成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "添加数少于1");

	// 结果状态
	private int state;
	// 结果详细信息
	private String stateInfo;

	// 构造函数
	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static ProductCategoryStateEnum stateOf(int index) {
		// 枚举的遍历函数vaues()
		for (ProductCategoryStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
