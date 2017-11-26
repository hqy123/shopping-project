package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult findItemParamByCatId(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.findItemParamByItemId(itemCatId);
		return result;
	}

	// 添加模板
	@RequestMapping("/save/{itemCatId}")
	@ResponseBody
	public TaotaoResult addItemParam(@PathVariable Long itemCatId, String paramData) {
		TaotaoResult result = itemParamService.addItemParam(itemCatId, paramData);
		return result;
	}
}
