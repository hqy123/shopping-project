package com.shopping.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.IDUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.mapper.TbItemDescMapper;
import com.shopping.mapper.TbItemMapper;
import com.shopping.mapper.TbItemParamItemMapper;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;
import com.shopping.pojo.TbItemExample;
import com.shopping.pojo.TbItemExample.Criteria;
import com.shopping.pojo.TbItemParamItem;
import com.shopping.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem findItemById(Long itemId) {
		//根据商品的id查询商品信息
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		
		List<TbItem> items = itemMapper.selectByExample(example);
		
		if(items!=null&&items.size()>0)
			return items.get(0);
		
		return null;
	}
	//分页查询商品
	@Override
	public EasyUIDataGridResult findItemsByPage(Integer page, Integer rows) {
		//查询所有商品的代码
		TbItemExample example = new TbItemExample();
		//Criteria criteria = example.createCriteria();
		
		PageHelper.startPage(page, rows);
		//本来是查询所有记录，被pagehelper一拦截就查询当前页的了
		List<TbItem> items = itemMapper.selectByExample(example);//limit 
		
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(items);
		
		//得到总记录数
		PageInfo<TbItem> info = new PageInfo(items);
		long total = info.getTotal();
		result.setTotal(total);
		
		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		
		//向tb_item表中添加商品信息
	    itemMapper.insert(item);
	    
	    
	    //向tb_item_desc表中添加商品描述信息
	    TbItemDesc itemDesc = new TbItemDesc();
	    itemDesc.setItemId(itemId);
	    itemDesc.setItemDesc(desc);
	    itemDesc.setCreated(new Date());
	    itemDesc.setUpdated(new Date());
	    
	    itemDescMapper.insert(itemDesc);
	    
	    //向tb_item_param_item表中添加规格数据
	    TbItemParamItem itemParamItem = new TbItemParamItem();
	    itemParamItem.setItemId(itemId);
	    itemParamItem.setParamData(itemParams);
	    itemParamItem.setCreated(new Date());
	    itemParamItem.setUpdated(new Date());
	    
	    itemParamItemMapper.insert(itemParamItem);
	    return TaotaoResult.ok();
	}

}
