package com.shopping.sso.service;

import com.shopping.common.utils.TaotaoResult;

public interface UserLoginService {

	TaotaoResult findUser(String username, String password);

}
