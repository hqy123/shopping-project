package com.shopping.sso.service;

import com.shopping.common.utils.TaotaoResult;

public interface TokenService {

	TaotaoResult findUserInfoByToken(String token);

}
