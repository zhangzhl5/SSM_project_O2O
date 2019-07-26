package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类描述：路由功能类
 * 在spring—web中定义的视图解析器会将路径信息补全
 * @author zhangzhl
 *
 */
@Controller
@RequestMapping(value = "shopadmin",method= {RequestMethod.GET})

public class ShopAdmincontroller {
	
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
		
	}
	
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		// 转发至店铺列表界面
		return "shop/shoplist";
		
	}
	
	@RequestMapping(value = "/shopmanage")
	public String shopManager() {
		// 转发至店铺管理界面
		return "shop/shopmanage";
		
	}
	
	@RequestMapping(value = "/productmanage")
	public String productManager() {
		// 转发至商品管理界面
		return "shop/productmanage";
		
	}
	
	@RequestMapping(value = "/productcategorymanage",method= {RequestMethod.GET})
	public String produCtcategoryManage() {
		// 转发至商品类别管理界面
		return "shop/productcategorymanage";
		
	}
	
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		// 转发至商品添加/编辑界面
		return "shop/productoperation";
	}

}
