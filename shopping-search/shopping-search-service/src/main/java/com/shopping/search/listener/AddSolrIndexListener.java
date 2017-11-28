package com.shopping.search.listener;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.support.logging.Log;
import com.shopping.common.pojo.SearchItem;
import com.shopping.search.mapper.ItemMapper;

public class AddSolrIndexListener implements MessageListener {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message msg) {
		try {
			TextMessage message = (TextMessage) msg;
			Long id = Long.parseLong(message.getText());
			
			Thread.sleep(1000);
			
			SearchItem item = itemMapper.getItemInfoById(id);
			
			SolrInputDocument doc = new SolrInputDocument();
			
			doc.setField("id",item.getId());
			doc.setField("item_title", item.getTitle());
			doc.setField("item_sell_point", item.getSell_point());
			doc.setField("item_image", item.getImage());
			doc.setField("item_price", item.getPrice());
			doc.setField("item_category_name", item.getCategory_name());
			
			solrServer.add(doc);
			solrServer.commit();
			System.out.println("add suceess");
			
		} catch (NumberFormatException | JMSException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
