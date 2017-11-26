package com.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String toIndex()
	{
		return "index";
	}
	
	@RequestMapping("{page}")//item-add
	public String toPage(@PathVariable String page)
	{
		return page;
	}
}
