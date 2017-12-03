package com.shopping.order.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.common.jedis.JedisClient;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.mapper.TbOrderItemMapper;
import com.shopping.mapper.TbOrderMapper;
import com.shopping.mapper.TbOrderShippingMapper;
import com.shopping.order.pojo.OrderInfo;
import com.shopping.order.service.OrderService;
import com.shopping.pojo.TbOrderItem;
import com.shopping.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	@Value("${REDIS_ORDER_ID}")
	private String REDIS_ORDER_ID;
	
	@Value("${REDIS_ORDERID_START}")
	private String REDIS_ORDERID_START;
	
	@Value("${REDIS_ORDER_DETAIL}")
	private String REDIS_ORDER_DETAIL;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private TbOrderMapper orderMapper; 
	
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		//向订单表中添加订单
				//生成订单id：redis incr
				Boolean boo = jedisClient.exists(REDIS_ORDER_ID);
				if(!boo){
					jedisClient.set(REDIS_ORDER_ID, REDIS_ORDERID_START);
				}
				String orderId = jedisClient.incr(REDIS_ORDER_ID).toString();
				
				orderInfo.setOrderId(orderId);
				orderInfo.setStatus(1);//未支付
				orderInfo.setCreateTime(new Date());
				orderInfo.setUpdateTime(new Date());
				
				//向tb_order表中添加订单
				orderMapper.insert(orderInfo);
				
				//向订单商品表中添加给订单的商品		
				List<TbOrderItem> items = orderInfo.getOrderItems();
				for(TbOrderItem item:items){
					String id = jedisClient.incr(REDIS_ORDER_DETAIL).toString();//生成id
					item.setId(id);
					item.setOrderId(orderId);
					orderItemMapper.insert(item);
				}
				
				//向收货地址表中添加地址
				TbOrderShipping shipping = orderInfo.getOrderShipping();
				shipping.setOrderId(orderId);
				shipping.setCreated(new Date());
				shipping.setUpdated(new Date());
				
				orderShippingMapper.insert(shipping);
				
				return TaotaoResult.ok(orderId);//返回订单号
	}

}
