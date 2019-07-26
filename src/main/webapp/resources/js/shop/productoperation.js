/**
 * 
 */
$(function() {
	// 如果前端传入的productId有值，则getQueryString这个方法定义在common.js中就能获取到
	var productId = getQueryString('productId');
	// 根据productId获取商品信息的url
	var infoUrl  = '/o2o/productadmin/getproductbyid?productId=' + productId;
	// 获取当前店铺设定的商品类别列表的url
	var categoryUrl = 'o2o/productadmin/getproductcategorylist';
	// 更新商品信息的URL
	var productPostUrl = 'o2o/productadmin/modifyproduct';
	
	var isEdit = false;
	if(productId){
		// 若有productId则为编辑动作
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory();
		productPostUrl = 'o2o/productadmin/addproduct';
	}
	// 获取需要编辑的商品的商品信息，并赋值给表单
	function getInfo(id) {
		$.getJSON(infoUrl,function(data) {
			if(data.success) {
				// 从返回的JSON中获取product对象信息，并赋值给表单
				var product = data.product;
				$('#product_name').val(product.productName);
				$('#product_desc').val(product.productDesc);
				$('#priority').val(product.priority);
				$('#normal_price').val(product.normalPrice);
				$('#promotion_price').val(product.promotionPrice);
				// 获取原本的商品类别以及该商铺的所有商品类别列表
				var optionHtml = '';
				var optionArr = data.productCategoryList;
				var optionSelected = product.productCategory.productCategoryId;
				// 生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
				optionArr.map(function(item,index){
					var isSelect = optionSelected === item.productCategoryId?'selected':'';
					optionHtml += '<option data-value="'
						+item.productCategoryId
						+'"'
						+isSelect
						+'>'
						+item.productCategoryName
						+'</option>';
				}) ;
				$('#category').html(optionHtml);
			}
		});
	}
	// 为商品添加操作提供该店铺下所有商品类别列表
	function getCategory() {
		$.getJSON(categoryUrl,function(data) {
			if(data.success) {
				var productCategoryList = data.data;
				var optionHtml = '';
				productCategoryList.map(function(item,index){
					optionHtml+='<option data-value="'
						+item.productCategoryId+'">'
						+item.productCategoryName+'</option>';					
				});
				$('#category').html(optionHtml);
			}
		});
	}
	
	// 针对商品详情图控件组，若该控件组最后一个元素发生里变化
	// 且控件总数未达到6个，则生成一个新的上传控件
	$('.detail-img-dev').on('change','.detail-img:last-child',function(){
		if($('.detail-img').length < 6) {
			$('.detail-img').append('<input type="file" class = "detail-img">');
		}
	});
	
	// 提交按钮的事件响应，分别对商品编辑和添加做不同的响应
		$('#submit').click(function(){
			// 创建商品JSON对象，并从表单里面获取对应的属性值
			 var product = {};
				$('#product_name').val(product.productName);
				$('#product_desc').val(product.productDesc);
				$('#priority').val(product.priority);
				$('#normal_price').val(product.normalPrice);
				$('#promotion_price').val(product.promotionPrice);
			 product.productName = $('#product-name').val();
			 product.productDesc = $('#product_desc').val();
			 product.priority = $('#priority').val();
			 product.normalPrice = $('#normal_price').val();
			 product.promotionPrice = $('#promotion_price').val();
			 // 获取选定的商品类别值
			 product.productCategory = {
				  productCategoryId : $('#category').find('option').not(function(){
					  return !this.selected;
				  }).data('value')
			 };
			 product.productId = productId;
			 // 获取缩略图文件流
			 var thumbnail = $('#small-img')[0].files[0];
			 // 生成表单对象，用于接收参数并且传递给后端
			 var formData = new FormData();
			 formData.append('thumbnail',thumbnail);
			 $('.detail-img').map(function(item,index){
				 //判断该控件是否选择了文件
				 if($('.detail-img')[index].files.length > 0){
					 // 将第i个文件流赋值给key为productImg的表单键值对里
					 formData.append('productImg'+index,$('.detail-img')[index].files[0]);
				 }
			 });
			 // 将productJSON对象转换成字符流保存至表单对象key为productStr的键值对里
			 formData.append('productStr',JSON.stringify(product)); // JSON 将product数据转化
			 // 获取表单里输入的验证码
			 var verifyCodeActual = $('#j_kaptcha').val();
			 if(!verifyCodeActual){
				 $.toast('请输入验证码');
				 return;
			 }
			 formData.append('verifyCodeActual',verifyCodeActual);
			 // 将数据提交给后台处理相关操作
			 $.ajax({
				 url : productPostUrl,
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