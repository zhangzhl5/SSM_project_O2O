package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：图片验证码校验工具类
 * @author zhangzhl
 *
 */
public class CodeUtil {
	
	/**
	 * 方法描述：提交时校验验证码输入是否正确
	 * @param request
	 * @return
	 */
	public static boolean checkVerifyCode(HttpServletRequest request) {
		// 获取期待输入的验证码
		String verifyCodeExcepted = (String)request.getSession().
				getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		// 实际输入的验证码
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if(verifyCodeActual == null|| !verifyCodeActual.equals(verifyCodeExcepted)) {
			return false;
		}
		return true;
		
	}
}
