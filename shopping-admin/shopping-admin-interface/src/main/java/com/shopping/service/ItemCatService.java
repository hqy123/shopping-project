package com.shopping.service;

import java.util.List;

import com.shopping.common.pojo.EasyUITreeNode;

public interface ItemCatService {

	List<EasyUITreeNode> findItemCatsById(Long parentId);

}
