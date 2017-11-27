package com.shopping.home.service;

import java.util.List;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbContent;

public interface ContentService {

	TaotaoResult addContent(TbContent content);

	List<TbContent> findContentsByCatId(Long cONTENT_AD);

}
