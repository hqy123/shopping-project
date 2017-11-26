package com.shopping.search.service;

import com.shopping.common.pojo.SearchResult;

public interface SearchResultService {

	SearchResult findItems(String keyword, Integer page, Integer pAGE_ROWS);

}
