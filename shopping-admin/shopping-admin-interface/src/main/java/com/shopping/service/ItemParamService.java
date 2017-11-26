package com.shopping.service;

import com.shopping.common.utils.TaotaoResult;

public interface ItemParamService {

	TaotaoResult findItemParamByItemId(Long itemCatId);

	TaotaoResult addItemParam(Long itemCatId, String paramData);

}
