package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.home.service.ContentCategoryService;

@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> findContentCategoryById(@RequestParam(name="id",defaultValue="0")Long parentId)
	{
		//根据内容分类的id查询子分类
		List<EasyUITreeNode> conCats = contentCategoryService.findCatsById(parentId);
		return conCats;
	}
	
	//添加分类
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId,String name){
		
		TaotaoResult conCat = contentCategoryService.addContentCategory(parentId, name);
		return conCat;
	}
}
