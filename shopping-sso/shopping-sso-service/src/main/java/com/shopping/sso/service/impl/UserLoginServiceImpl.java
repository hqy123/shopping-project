package com.shopping.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.shopping.common.jedis.JedisClient;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.mapper.TbUserMapper;
import com.shopping.pojo.TbUser;
import com.shopping.pojo.TbUserExample;
import com.shopping.pojo.TbUserExample.Criteria;
import com.shopping.sso.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_SESSION_EXPIRE}")
	private Integer REDIS_SESSION_EXPIRE;
	
	@Override
	public TaotaoResult findUser(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();

		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list.size() <= 0 || list == null) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		// 得到查询出的用户
		TbUser user = list.get(0);

		// 判断密码是否正确
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}

		// 登录成功：把用户信息放到session，使用Redis模拟session
		// 生成一个唯一的相当于SESSIONId的数值,我们称为token
		String token = UUID.randomUUID().toString();

		// 把token作为键，用户信息作为值放入redis
		user.setPassword(null);
		jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));

		// 设置session的过期时间
		jedisClient.expire("SESSION:" + token, REDIS_SESSION_EXPIRE);

		return TaotaoResult.ok(token);// 返回token
	}

}
