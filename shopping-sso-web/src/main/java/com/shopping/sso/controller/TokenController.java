package com.shopping.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.sso.service.TokenService;

@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object findUserInfoByToken(@PathVariable String token,String callback){
	
		TaotaoResult r = tokenService.findUserInfoByToken(token);

		//判断是否使用了jsonp
		if(StringUtils.isNotBlank(callback)){
		   MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(r);
		   mappingJacksonValue.setJsonpFunction(callback);
		   return mappingJacksonValue;
		}
		return r;
	}
}
