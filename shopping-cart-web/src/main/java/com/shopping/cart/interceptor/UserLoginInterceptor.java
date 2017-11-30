package com.shopping.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shopping.common.utils.CookieUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.sso.service.TokenService;

public class UserLoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse rep, Object arg2) throws Exception {
		String token = CookieUtils.getCookieValue(req, "TT_TOKEN");
		
		if(StringUtils.isBlank(token)) {
			return true;
		}
		
		TaotaoResult result = tokenService.findUserInfoByToken(token);
		if(result.getStatus() != 200) {
			return true;
		}
		
		req.setAttribute("user", result.getData());
		
		return true;
	}

}
