package com.shopping.item.pojo;

import com.shopping.pojo.TbItem;

public class Item extends TbItem {
	
	public Item(TbItem tbItem){
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
	    this.setCid(tbItem.getCid());
	    this.setStatus(tbItem.getStatus());
	    this.setCreated(tbItem.getCreated());
	    this.setUpdated(tbItem.getUpdated());
	}
	
	public String[] getImages()
	{
		String img = this.getImage();
		if(img!=null &&!"".equals(img)){
			String[] arr = img.split(",");
			return arr;
		}
		return null;
	}

}
