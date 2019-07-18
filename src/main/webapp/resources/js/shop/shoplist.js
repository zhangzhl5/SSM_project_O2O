$(function () {

	function getlist(e) {
		$.ajax({
			url : "/o2o/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.shopList);
					handleUser(data.user);
				}
			}
		});
	}

	function handleUser(data) {
		$('#user-name').text(data.name);
	}

	function handleList(data) {
		var html = '';
		data.map(function (item, index) {
			html += '<div class="row row-shop"><div class="col-40">'+ 
			item.shopName +'</div><div class="col-40">'+ 
			shopStatus(item.enableStatus) +'</div><div class="col-20">'+
			goShop(item.enableStatus, item.shopId) +'</div></div>';
		});
		$('.shop-wrap').html(html);
	}
	// 根据店铺状态校验是否应该显示进入店铺管理页面
	function goShop(status, id) {
		if (status == 1) {
			return '<a href="/o2o/shopadmin/shopmanage?shopId='+ id +'">进入</a>';
		} else {
			return '';
		}
	}
   // 根据状态值返回对应的名称
	function shopStatus(status) {
		if (status == 0) {
			return '审核中';
		} else if (status == -1) {
			return '店铺非法';
		} else if (status == 1){
			return '审核通过';
		}
	}


	$('#log-out').click(function () {
		$.ajax({
			url : "/o2o/shopadmin/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/shopadmin/ownerlogin';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});


	getlist();
});
