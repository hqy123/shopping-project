package com.shopping.cart.service;

import java.util.List;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbItem;

public interface CartService {

	TaotaoResult addItemToCart(long itemId, long itemId2, Integer num);

	TaotaoResult cookieMergeToRedis(Long id, List<TbItem> jsonToList);

	List<TbItem> showCartItems(Long id);

	TaotaoResult updateCartNumber(Long id, long itemId, int itemNum);

	TaotaoResult deleteCartItem(Long id, long itemId);

}
