package com.shopping.home.service;

import java.util.List;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.utils.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUITreeNode> findCatsById(Long parentId);

	TaotaoResult addContentCategory(Long parentId, String name);

}
