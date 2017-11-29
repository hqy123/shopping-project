package com.shopping.item.listener;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.soap.Text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.shopping.item.pojo.Item;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;
import com.shopping.service.ItemService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class AddItemTmpListener implements MessageListener{
	@Value("${GEN_HTML_UTL}")
	private String GEN_HTML_UTL;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfig  freeMarkerConfig;

	@Override
	public void onMessage(Message msg) {
		
		try {
			TextMessage message = (TextMessage) msg;
			long id = Long.parseLong(message.getText());
			
			Thread.sleep(1000);
			
			TbItem tbItem = itemService.findItemInfoById(id);
			Item item = new Item(tbItem);
			TbItemDesc tbItemDesc = itemService.findItemDescInfoById(id);
			
			Map map = new HashMap();
			map.put("item", item);
			map.put("itemDesc",  tbItemDesc);
			
			//根据FreeMarkerConfigurer对象得到Configuration对象
			Configuration config = freeMarkerConfig.getConfiguration();
			Template template = config.getTemplate("item.ftl");
			
			//静态页面的路径和名称
			Writer out = new FileWriter(GEN_HTML_UTL+id+".html");
			template.process(map, out);
			System.out.println("add success");
			out.close();
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
