package com.imooc.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;

/**
 * 类描述：商品类别的控制层
 * 主要包括：1、获取商品类别的列表
 * 		   2、添加商品种类
 * 		   3、删除商品种类
 * @author zhangzhl
 *
 */
@Controller
// 一级路由
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 * 方法描述：获取商品种类
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET )
	@ResponseBody
	private Result<List<ProductCategory>>  getProductCategoryList(HttpServletRequest request){
		// TODO 目前还没有实现登陆功能 ，登陆的session还不能获取到
//		Shop shop = new Shop();
//		shop.setShopId(2l);
//		// 手动设置session
//		request.getSession().setAttribute("currentShop", shop);
		// 获取当前商铺
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory>  list = null;
		// 如果当前商铺为空说明没有商铺的操作权限，返回错误的信息
		if(currentShop != null && currentShop.getShopId() > 0) {
			// 如果有值拿到shopID 返回该商铺的商品种类
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>> (true,list);
		} else {
			// 错误的信息
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>> (false,ps.getState(),ps.getStateInfo());
		}
	}
	
	/**
	 * 方法描述：增加商品种类
	 * @param productCategoryList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST )
	@ResponseBody
	private Map<String,Object>  addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Shop currentShop =  (Shop) request.getSession().getAttribute("currentShop");
		// 商品类别是在那个店铺下创建的
		for(ProductCategory  pc : productCategoryList ) {
			pc.setShopId(currentShop.getShopId());
		}
		if(productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请至少输入一个商品类别");
		}
		return modelMap;
		
	}
	
	/**
	 * 方法描述：删除商品种类
	 * @param productCategoryId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST )
	@ResponseBody
	private Map<String,Object>  removeProductCategory(Long productCategoryId,
			HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Shop currentShop =  (Shop) request.getSession().getAttribute("currentShop");
		if(productCategoryId  != null && productCategoryId > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请至少选择一个商品类别");
		}
		return modelMap;
		
	}

}
