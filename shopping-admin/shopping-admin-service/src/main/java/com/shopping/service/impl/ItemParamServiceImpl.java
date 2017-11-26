package com.shopping.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.mapper.TbItemParamMapper;
import com.shopping.pojo.TbItemParam;
import com.shopping.pojo.TbItemParamExample;
import com.shopping.pojo.TbItemParamExample.Criteria;
import com.shopping.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	//根据商品分类的id查询模板
	@Override
	public TaotaoResult findItemParamByItemId(Long itemCatId) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(itemCatId);
		
		//该方法没有查询模板字段
		List<TbItemParam> itemParam = itemParamMapper.selectByExampleWithBLOBs(example);
		if(itemParam!=null&&itemParam.size()>0){
			return TaotaoResult.ok(itemParam.get(0));
		}
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult addItemParam(Long itemCatId, String paramData) {
		TbItemParam itemParam = new TbItemParam();
		
		itemParam.setItemCatId(itemCatId);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		
		itemParamMapper.insert(itemParam);
		
		return TaotaoResult.ok();
	}

}
