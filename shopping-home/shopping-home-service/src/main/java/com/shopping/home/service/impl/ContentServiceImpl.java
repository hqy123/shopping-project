package com.shopping.home.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.shopping.common.jedis.JedisClient;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.home.service.ContentService;
import com.shopping.mapper.TbContentMapper;
import com.shopping.pojo.TbContent;

public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_REDIS_KEY}")
	private String CONTENT_REDIS_KEY;
	@Override
	public TaotaoResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		
		contentMapper.insert(content);
		//缓存中的数据和数据库中的数据不一致，需要做缓存同步
		jedisClient.hdel(CONTENT_REDIS_KEY, content.getCategoryId()+"");
		
		return TaotaoResult.ok();
	}

}
