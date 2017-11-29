package com.shopping.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.utils.CookieUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.sso.service.UserLoginService;

@Controller
public class UserLoginController {
	
	@Autowired
	private UserLoginService loginService;
	
	@RequestMapping("/page/login")
	public String toLoginPage()
	{
		return "login";
	}
	
	//登录功能
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username,String password,HttpServletRequest request,HttpServletResponse response)
	{
		TaotaoResult r = loginService.findUser(username, password);
		
		if(r.getStatus()==200){
			//登录成功把token放入cookie
		    String token = r.getData().toString();
		    CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		}
		return r;
	}
}
