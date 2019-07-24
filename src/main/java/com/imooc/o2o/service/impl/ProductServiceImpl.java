package com.imooc.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.PageCalculator;

/**
 * 商品接口的实现类
 * @author zhangzhl
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	/**
	 * 获取商品列表
	 * @see com.imooc.o2o.service.ProductService#getProductList(com.imooc.o2o.entity.Product, int, int)
	 */
	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

	/**
	 * 根据ID查询商品
	 * @see com.imooc.o2o.service.ProductService#getProductById(long)
	 */
	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductByProductId(productId);
	}

	/**
	 * 添加商品
	 * @see com.imooc.o2o.service.ProductService#addProduct(com.imooc.o2o.entity.Product, org.springframework.web.multipart.commons.CommonsMultipartFile, java.util.List)
	 */
	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs) throws RuntimeException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品失败:" + e.toString());
			}
			if (productImgs != null && productImgs.size() > 0) {
				addProductImgs(product, productImgs);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 修改商品信息
	 * @see com.imooc.o2o.service.ProductService#modifyProduct(com.imooc.o2o.entity.Product, org.springframework.web.multipart.commons.CommonsMultipartFile, java.util.List)
	 */
	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product,ImageHolder thumbnail, List<ImageHolder> productImgs) 
			throws RuntimeException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setLastEditTime(new Date());
			if (thumbnail != null) {
				Product tempProduct = productDao.queryProductByProductId(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
//					FileUtil.deleteFile(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			if (productImgs != null && productImgs.size() > 0) {
				deleteProductImgs(product.getProductId());
				addProductImgs(product, productImgs);
			}
			try {
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new RuntimeException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new RuntimeException("更新商品信息失败:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void addProductImgs(Product product, List<ImageHolder> productImgs ) {
//		String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
//		List<String> imgAddrList = ImageUtil.generateNormalImgs(productImgs, dest);
//		if (imgAddrList != null && imgAddrList.size() > 0) {
//			List<ProductImg> productImgList = new ArrayList<ProductImg>();
//			for (String imgAddr : imgAddrList) {
//				ProductImg productImg = new ProductImg();
//				productImg.setImgAddr(imgAddr);
//				productImg.setProductId(product.getProductId());
//				productImg.setCreateTime(new Date());
//				productImgList.add(productImg);
//			}
//			try {
//				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
//				if (effectedNum <= 0) {
//					throw new RuntimeException("创建商品详情图片失败");
//				}
//			} catch (Exception e) {
//				throw new RuntimeException("创建商品详情图片失败:" + e.toString());
//			}
//		}
	}

	private void deleteProductImgs(long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		for (ProductImg productImg : productImgList) {
//			FileUtil.deleteFile(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}

	private void addThumbnail(Product product, ImageHolder productImgs) {
//		String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
//		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
//		product.setImgAddr(thumbnailAddr);
	}
}
