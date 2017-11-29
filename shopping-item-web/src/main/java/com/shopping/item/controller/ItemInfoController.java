package com.shopping.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.item.pojo.Item;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;
import com.shopping.service.ItemService;

@Controller
public class ItemInfoController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{id}")
	public String itemInfo(@PathVariable long id,Model model) {
		
		TbItem tbItem = itemService.findItemInfoById(id);
		Item item = new Item(tbItem);
		
		TbItemDesc tbItemDesc = itemService.findItemDescInfoById(id);
		
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		
		return "item";
		
	}
}
