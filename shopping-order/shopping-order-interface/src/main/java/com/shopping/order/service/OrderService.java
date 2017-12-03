package com.shopping.order.service;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.order.pojo.OrderInfo;

public interface OrderService {


	TaotaoResult createOrder(OrderInfo orderInfo);

}
