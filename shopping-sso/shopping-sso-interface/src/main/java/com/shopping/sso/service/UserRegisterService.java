package com.shopping.sso.service;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbUser;

public interface UserRegisterService {

	TaotaoResult checkData(String param, Integer type);

	TaotaoResult addUser(TbUser user);

}
