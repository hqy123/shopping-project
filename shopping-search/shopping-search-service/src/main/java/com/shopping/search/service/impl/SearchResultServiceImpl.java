package com.shopping.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.common.pojo.SearchResult;
import com.shopping.search.dao.SearchDao;
import com.shopping.search.service.SearchResultService;

@Service
public class SearchResultServiceImpl implements SearchResultService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult findItems(String keyword, Integer page, Integer rows) {
		SolrQuery query = new SolrQuery();
		// 设置搜索什么
		query.setQuery(keyword);

		if (page < 0)
			page = 1;
		query.setStart((page - 1) * rows);
		query.setRows(rows);

		// 设置从哪个索引上搜
		query.set("df", "item_keywords");

		// 设置标题被高亮及怎么高亮
		query.setHighlight(true);// 使用高亮
		query.addHighlightField("item_title");// 设置哪个索引对应的数据被高亮
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		SearchResult result = null;
		try {
			result = searchDao.searchItem(query);
			long totalCount = result.getTotalCount();
			int totalPage =(int) (totalCount /rows);
			if(totalCount%rows!=0)
				totalPage++;
			result.setTotalPage(totalPage);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			System.out.println(3);
			e.printStackTrace();
		}
		return result;
	}

}
