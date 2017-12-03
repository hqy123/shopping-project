package com.shopping.order.pojo;

import java.util.List;

import com.shopping.pojo.TbOrder;
import com.shopping.pojo.TbOrderItem;
import com.shopping.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder{
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
