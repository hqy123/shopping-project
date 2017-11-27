package com.shopping.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.home.service.ContentService;
import com.shopping.pojo.TbContent;

@Controller
public class HomeController {
	@Autowired
	private ContentService contentService;
	
	@Value("${CONTENT_AD}")
	private Long CONTENT_AD;
	  
	@RequestMapping("index")
	public String homePage(Model model) {
		
		List<TbContent> list = contentService.findContentsByCatId(CONTENT_AD);
		
		model.addAttribute("imgList", list);
		
		return "index";
		
	}
}	
