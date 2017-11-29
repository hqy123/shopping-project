package com.shopping.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.common.jedis.JedisClient;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbUser;
import com.shopping.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_SESSION_EXPIRE}")
	private Integer REDIS_SESSION_EXPIRE;

	@Override
	public TaotaoResult findUserInfoByToken(String token) {
		String json = jedisClient.get("SESSION:" + token);

		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "登录已过期");
		}

		// 重置session
		jedisClient.expire("SESSION:" + token, REDIS_SESSION_EXPIRE);

		return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

}
