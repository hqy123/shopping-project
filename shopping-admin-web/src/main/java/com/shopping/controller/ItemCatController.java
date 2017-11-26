package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.service.ItemCatService;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	List<EasyUITreeNode> findItemCats(@RequestParam(name="id",defaultValue="0")Long parentId)
	{
		List<EasyUITreeNode> nodes = itemCatService.findItemCatsById(parentId);
		return nodes;
	}
}
