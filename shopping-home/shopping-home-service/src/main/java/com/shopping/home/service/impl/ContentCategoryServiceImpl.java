package com.shopping.home.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.home.service.ContentCategoryService;
import com.shopping.mapper.TbContentCategoryMapper;
import com.shopping.pojo.TbContentCategory;
import com.shopping.pojo.TbContentCategoryExample;
import com.shopping.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper conCatMapper;
	
	//根据分类的id查询子分类
	@Override
	public List<EasyUITreeNode> findCatsById(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> conCats = conCatMapper.selectByExample(example);
		List<EasyUITreeNode> nodes =new ArrayList<>();
		for(TbContentCategory conCat:conCats){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(conCat.getId());
			node.setText(conCat.getName());
			node.setState(conCat.getIsParent()?"closed":"open");
			
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	public TaotaoResult addContentCategory(Long parentId, String name) {
		TbContentCategory conCat = new TbContentCategory();
		
		conCat.setParentId(parentId);
		conCat.setName(name);
		conCat.setStatus(1);
		conCat.setSortOrder(1);
		conCat.setIsParent(false);
		conCat.setCreated(new Date());
		conCat.setUpdated(new Date());
		
		//这行代码执行完会把数据库生成的id的值赋给conCat对象的id属性
		conCatMapper.insert(conCat);
		
		//判断父分类是不是父分类
		//得到父分类对象
		TbContentCategory parentCat = conCatMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()){
			//不是父分类要修改为是父分类
			parentCat.setIsParent(true);
			parentCat.setUpdated(new Date());
			//更新到数据库
			conCatMapper.updateByPrimaryKey(parentCat);
		}
		
		return TaotaoResult.ok(conCat);
	}
}

