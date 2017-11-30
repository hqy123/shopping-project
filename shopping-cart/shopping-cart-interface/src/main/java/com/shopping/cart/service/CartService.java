package com.shopping.cart.service;

import java.util.List;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbItem;

public interface CartService {

	TaotaoResult addItemToCart(long itemId, long itemId2, Integer num);

	List<TbItem> showCartItems();

}
