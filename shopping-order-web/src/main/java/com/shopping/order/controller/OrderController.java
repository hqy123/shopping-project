package com.shopping.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.cart.service.CartService;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.order.pojo.OrderInfo;
import com.shopping.order.service.OrderService;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbUser;

@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String order(HttpServletRequest req) {
		TbUser user = (TbUser) req.getAttribute("user");
		
		List<TbItem> items = cartService.showCartItems(user.getId());
		req.setAttribute("cartList", items);
		
		return "order-cart";
	}
	
	@RequestMapping("/order/create")
	public String createOrder(HttpServletRequest req,OrderInfo orderInfo) {
		TbUser user = (TbUser) req.getAttribute("user");
		
		orderInfo.setBuyerNick(user.getUsername());
		orderInfo.setUserId(user.getId());
		
		TaotaoResult result = orderService.createOrder(orderInfo);
		
		req.setAttribute("orderId", result.getData().toString());
		req.setAttribute("payment", orderInfo.getPayment());
		return "success";
		
	}
}
