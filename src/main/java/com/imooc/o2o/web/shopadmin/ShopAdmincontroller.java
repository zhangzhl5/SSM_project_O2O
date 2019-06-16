package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin",method= {RequestMethod.GET})

public class ShopAdmincontroller {
	
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
		
	}
	
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		return "shop/shoplist";
		
	}
	
	@RequestMapping(value = "/shopmanage")
	public String shopManager() {
		return "shop/shopmanage";
		
	}
	
	@RequestMapping(value = "/productmanage")
	public String productManager() {
		return "shop/productmanage";
		
	}
	
	@RequestMapping(value = "/productcategorymanage",method= {RequestMethod.GET})
	public String produCtcategoryManage() {
		return "shop/productcategorymanage";
		
	}

}
