package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbItem;
import com.shopping.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem findItemById(@PathVariable Long itemId) {
		TbItem item = itemService.findItemById(itemId);
		return item;
	}

	// 分页查询商品
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult findItemByPage(Integer page, Integer rows) {
		// 接收easyui传递过来的page和rows参数的值
		EasyUIDataGridResult result = itemService.findItemsByPage(page, rows);
		return result;
	}

	// 添加商品
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
		TaotaoResult result = itemService.addItem(item, desc, itemParams);
		return result;
	}
}
