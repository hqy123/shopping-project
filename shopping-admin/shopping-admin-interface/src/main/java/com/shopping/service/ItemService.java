package com.shopping.service;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;

public interface ItemService {

	TbItem findItemById(Long itemId);

	EasyUIDataGridResult findItemsByPage(Integer page, Integer rows);

	TaotaoResult addItem(TbItem item, String desc, String itemParams);

	TbItem findItemInfoById(long id);

	TbItemDesc findItemDescInfoById(long id);

}
