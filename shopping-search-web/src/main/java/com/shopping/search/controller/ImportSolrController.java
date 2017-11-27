package com.shopping.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.utils.TaotaoResult;
import com.shopping.search.service.ImportSolrService;

@Controller
public class ImportSolrController {
	
	@Autowired
	private ImportSolrService importSolr;
	
	@RequestMapping("/importSolr")
	@ResponseBody
	public TaotaoResult importSolr() {
		TaotaoResult result = importSolr.importData();
		System.out.println(result.getStatus());
		return result;
		
	}
	 
}
