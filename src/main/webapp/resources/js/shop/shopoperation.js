/**
 * 
 */
$(function() {
	// 如果前端传入的shopID有值，则getQueryString这个方法定义在common.js中就能获取到
	var shopId = getQueryString('shopId');
	var isEdit = shopId?true:false;
	// 初始化RUL
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	// 注册店铺URL
	var registerShopUrl = '/o2o/shopadmin/registershop';
	// 根据shopId请求后台
	var shopInfoUrl  = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	var editShopUrl = '/o2o/shopadmin/modifyshop';
	if(!isEdit){
		getShopInitInfo();
	} else {
		getShopInfo(shopId);
	}
	
	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl,function(data) {
			if(data.success) {
				var shop = data.shop;
				 shop.shopName = $('#shop-name').val(shop.shopName);
				 shop.shopAddr = $('#shop-addr').val(shop.shopAddr);
				 shop.phone = $('#shop-phone').val(shop.phone);
				 shop.shopDesc = $('#shop-desc').val(shop.shopDesc);
				 var shopCategory = '<option data-id="'
					 +shop.shopCategory.shopCategoryId +'" selected>'
					 +shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item,index) {
					tempAreaHtml += '<option data-id="' +item.areaId + '">'
					+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				$('#area').attr('data-id',shop.areaId);
			}
		});
	}
	function getShopInitInfo() {
		$.getJSON(initUrl,function(data) {
			if(data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				// 初始化商铺分类的下拉选择数据
				data.shopCategoryList.map(function(item,index) {
					tempHtml += '<option data-id="' +item.shopCategoryId + '">'
					+ item.shopCategoryName + '</option>';
				});
				// 初始化区域分类的下拉选择数据
				data.areaList.map(function(item,index) {
					tempAreaHtml += '<option data-id="' +item.areaId + '">'
					+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
		$('#submit').click(function(){
			 var shop = {};
			 if(isEdit){
				 shop.shopId = shopId;
			 }
			 shop.shopName = $('#shop-name').val();
			 shop.shopAddr = $('#shop-addr').val();
			 shop.phone = $('#shop-phone').val();
			 shop.shopDesc = $('#shop-desc').val();
			 shop.shopCategory = {
				  shopCategoryId : $('#shop-category').find('option').not(function(){
					  return !this.selected;
				  }).data('id')
			 };
			 shop.area = {
					 areaId : $('#area').find('option').not(function(){
						  return !this.selected;
					  }).data('id')
			 };
			 var shopImg = $('#shop-img')[0].files[0];
			 var formData = new FormData();
			 formData.append('shopImg',shopImg);
			 formData.append('shopStr',JSON.stringify(shop)); // JSON 将shop数据转化
			 var verifyCodeActual = $('#j_kaptcha').val();
			 if(!verifyCodeActual){
				 $.toast('请输入验证码');
				 return;
			 }
			 formData.append('verifyCodeActual',verifyCodeActual);
			 $.ajax({
				 url :(isEdit?editShopUrl: registerShopUrl),
				 type : 'POST',
				 data : formData,
				 contentType :false,
				 processData : false,
				 cache : false,
				 success : function(data) {
					 if(data.success) {
						 $.toast('提交成功');
					 } else {
						 $.toast('提交失败' + data.errMsg);
					 }
					 $('#kaptcha_img').click();
				 }
			 })
		});
}) 