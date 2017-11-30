package com.shopping.cart.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.cart.service.CartService;
import com.shopping.common.jedis.JedisClient;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.mapper.TbItemMapper;
import com.shopping.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {

	@Autowired 
	private JedisClient jedisClient;
	
	@Value("${REDIS_CART_KEY}")
	private String REDIS_CART_KEY;
	
	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public TaotaoResult addItemToCart(long userId, long itemId, Integer num) {
		
		if(jedisClient.hexists(REDIS_CART_KEY+":"+userId, itemId+"")) {
			String item = jedisClient.hget(REDIS_CART_KEY+":"+userId, itemId+"");
			
			if(StringUtils.isNotBlank(item)) {
				TbItem tbItem = JsonUtils.jsonToPojo(item, TbItem.class);
				tbItem.setNum(tbItem.getNum()+num);
				
				jedisClient.hset(REDIS_CART_KEY+":"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
			} 
			
		}else {
			TbItem item = itemMapper.selectByPrimaryKey(itemId);
			item.setNum(num);
			item.setImage(item.getImage().split(",")[0]);
			
			jedisClient.hset(REDIS_CART_KEY+":"+userId, itemId+"", JsonUtils.objectToJson(item));
			
		}
		return TaotaoResult.ok();
	}

	@Override
	public List<TbItem> showCartItems() {
		
		
		return null;
	}
	
	public TaotaoResult cookieMergeToRedis() {
		
		
		return TaotaoResult.ok();
	}
	

}
