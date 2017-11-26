package com.shopping.common.pojo;

import java.io.Serializable;
import java.util.List;

//根据搜索结果封装的实体类
public class SearchResult implements Serializable {
	
	private long totalCount;//商品的总个数
	private int totalPage;//总页数
	private List<SearchItem> items;//当前页的商品
	
	public long getTotalCount() {
		return totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public List<SearchItem> getItems() {
		return items;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setItems(List<SearchItem> items) {
		this.items = items;
	}

}
