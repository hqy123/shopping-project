package com.shopping.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.common.pojo.SearchResult;
import com.shopping.search.service.SearchResultService;


@Controller
public class SearchController {
	
	@Autowired
	private SearchResultService searchResultService;
	
	@Value("${PAGE_ROWS}")
	private Integer PAGE_ROWS;
	
	@RequestMapping("/search")
	public String findItems(String keyword,@RequestParam(defaultValue="1")Integer page,Model model)
	{
		//处理中文呢乱码：
		try {
			keyword=new String(keyword.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		SearchResult result = searchResultService.findItems(keyword, page, PAGE_ROWS);
		
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages",result.getTotalPage());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount",result.getTotalCount());
		model.addAttribute("itemList", result.getItems());
		
		return "search";
	}

}



