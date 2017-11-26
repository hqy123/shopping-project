package com.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.mapper.TbItemCatMapper;
import com.shopping.pojo.TbItemCat;
import com.shopping.pojo.TbItemCatExample;
import com.shopping.pojo.TbItemCatExample.Criteria;
import com.shopping.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private TbItemCatMapper  itemCatMapper;
	
	//根据分类的id查询子分类 
	@Override
	public List<EasyUITreeNode> findItemCatsById(Long parentId) {
		TbItemCatExample  example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
		
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for(TbItemCat itemCat:itemCats){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent()?"closed":"open");
			
			nodes.add(node);
		}
		return nodes;
	}
}
