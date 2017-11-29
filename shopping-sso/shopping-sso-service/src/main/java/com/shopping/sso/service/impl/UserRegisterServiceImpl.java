package com.shopping.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.mapper.TbUserMapper;
import com.shopping.pojo.TbUser;
import com.shopping.pojo.TbUserExample;
import com.shopping.pojo.TbUserExample.Criteria;
import com.shopping.sso.service.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	private TbUserMapper userMapper;

	@Override
	public TaotaoResult checkData(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();

		if (type.intValue() == 1)
			criteria.andUsernameEqualTo(param);
		else if (type.intValue() == 2)
			criteria.andPhoneEqualTo(param);
		else if (type.intValue() == 3)
			criteria.andEmailEqualTo(param);
		else
			return TaotaoResult.build(400, "类型不对");

		List<TbUser> user = userMapper.selectByExample(example);

		if (user == null || user.size() <= 0) {
			return TaotaoResult.ok(true);// 没查询出来表示数据可用
		}

		return TaotaoResult.ok(false);// 查询出来表示数据可用
	}

	@Override
	public TaotaoResult addUser(TbUser user) {
		// 先判断数据是否为空
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
				|| StringUtils.isBlank(user.getPhone())) {
			return TaotaoResult.build(400, "数据不能为空");
		}
		// 判断数据是否可用
		TaotaoResult r = checkData(user.getUsername(), 1);
		if (!(boolean) r.getData())
			return TaotaoResult.build(400, "用户名不可用");

		r = checkData(user.getPhone(), 2);
		if (!(boolean) r.getData())
			return TaotaoResult.build(400, "电话不可用");

		// 补全数据
		user.setCreated(new Date());
		user.setUpdated(new Date());

		// 对密码进行MD5加密
		String pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(pass);

		userMapper.insert(user);
		return TaotaoResult.ok();
	}

}
