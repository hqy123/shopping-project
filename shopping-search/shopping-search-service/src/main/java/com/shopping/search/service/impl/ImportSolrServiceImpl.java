package com.shopping.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.common.pojo.SearchItem;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.search.mapper.ItemMapper;
import com.shopping.search.service.ImportSolrService;

@Service
public class ImportSolrServiceImpl implements ImportSolrService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public TaotaoResult importData() {
		
		List<SearchItem> list = itemMapper.getItemInfo();
		
		try {
			for (SearchItem item : list) {
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", item.getId());
				document.setField("item_title",item.getTitle());
				document.setField("item_sell_point",item.getSell_point());
				document.setField("item_price", item.getPrice());
				document.setField("item_image", item.getImage());
				document.setField("item_category_name", item.getCategory_name());
				
				solrServer.add(document);
			}
			solrServer.commit();
			
			return TaotaoResult.ok();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TaotaoResult.build(201, "添加失败");
		} 
		
	}

}
