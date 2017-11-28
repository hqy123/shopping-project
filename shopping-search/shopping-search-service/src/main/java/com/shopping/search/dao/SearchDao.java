package com.shopping.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shopping.common.pojo.SearchItem;
import com.shopping.common.pojo.SearchResult;

@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult searchItem(SolrQuery query) throws SolrServerException {
		
		QueryResponse res = solrServer.query(query);
		
		SolrDocumentList documentList = res.getResults();
		
		SearchResult result = new SearchResult();
		result.setTotalCount(documentList.getNumFound());
		
		Map<String, Map<String, List<String>>>  highlighting = res.getHighlighting();
		
		List<SearchItem> list = new ArrayList<>();
		for (SolrDocument doc : documentList) {
			SearchItem item = new SearchItem();
    		item.setId((String)doc.get("id"));
    		item.setSell_point((String)doc.get("item_sell_point"));
    		item.setPrice((long)doc.get("item_price"));
    		item.setImage((String)doc.get("item_image"));
    		item.setCategory_name((String)doc.get("item_category_name"));
    		
    		//取高亮的标题
    		List<String> title = highlighting.get(doc.get("id")).get("item_title");
    		if(title!=null&&title.size()>0){
    			item.setTitle(title.get(0));
    		}else{
    			//没取到高亮的数据直接去文档中的标题
    			item.setTitle((String)doc.get("item_title"));
    		}
    		
    		list.add(item);
		}
		result.setItems(list);
			
		return result;
	}
}
