package com.shopping.home.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.common.jedis.JedisClient;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.home.service.ContentService;
import com.shopping.mapper.TbContentMapper;
import com.shopping.pojo.TbContent;
import com.shopping.pojo.TbContentExample;
import com.shopping.pojo.TbContentExample.Criteria;

@Service
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
		jedisClient.hdel(CONTENT_REDIS_KEY, "conetnt:"+content.getCategoryId());
		
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> findContentsByCatId(Long id) {
		
		List<TbContent> list = null;
		try {
			String json = jedisClient.hget(CONTENT_REDIS_KEY, "conetnt:"+id);
			if(StringUtils.isNoneBlank(json)) {
				list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		
		list = contentMapper.selectByExample(example);
	
		try {
			jedisClient.hset(CONTENT_REDIS_KEY, "conetnt:"+id, JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return list;
	}

}
