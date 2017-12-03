package com.shopping.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shopping.cart.service.CartService;
import com.shopping.common.utils.CookieUtils;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbUser;
import com.shopping.sso.service.TokenService;

public class UserLoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private  TokenService tokenService;
	@Autowired
	private CartService cartService;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		
		String token = CookieUtils.getCookieValue(req, "TT_TOKEN");
		if(StringUtils.isBlank(token)) {
			res.sendRedirect("http://localhost:8088/page/login?redirect="+req.getRequestURI());
			return false;
		}
		
		TaotaoResult result = tokenService.findUserInfoByToken(token);
		if(result.getStatus() != 200) {
			res.sendRedirect("http://localhost:8088/page/login?redirect="+req.getRequestURI());
			return false;
		}
		
		TbUser user = (TbUser) result.getData();
		
		req.setAttribute("user", user);
		
		String carts = CookieUtils.getCookieValue(req, "cart",true);
		
		if(StringUtils.isNotBlank(carts)) {
			cartService.cookieMergeToRedis(user.getId(), JsonUtils.jsonToList(carts, TbItem.class));
		}

		return true;
	}


}
