package com.shopping.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbUser;
import com.shopping.sso.service.UserRegisterService;

@Controller
public class UserRegisterController {
	
	@Autowired
	private UserRegisterService userRegisterService;
	
	@RequestMapping("/page/register")
	public String toRegisterPage()
	{
		return "register";
	}
	
	
	//验证数据是否可用
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkData(@PathVariable String param,@PathVariable Integer type){
		TaotaoResult r = userRegisterService.checkData(param, type);
		return r;
	}
	
	//注册
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user){
		System.out.println("register");
		return userRegisterService.addUser(user);
	}
	
}
