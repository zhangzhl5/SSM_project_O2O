$(function () {
	// 商铺信息按钮跳转获取不到shopId
	var shopId = getQueryString('shopId');
	var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId='+shopId;
	$.getJSON(shopInfoUrl,function(data){
		if(data.redirect) {
			window.location.href = data.url;
		} else {
			if(data.shopId != undefined && data.shopId != null){
					shopId = data.shopId;
					window.location.href = '/o2o/shopadmin/shopoperation?shopId='+shopId;
			}	
//			$('#shopInfo')
//				.attr('href','/o2o/shopadmin/shopoperation?shopId='+shopId);
		}
	});
});