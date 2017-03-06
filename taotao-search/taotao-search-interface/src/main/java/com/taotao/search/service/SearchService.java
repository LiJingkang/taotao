package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

/**
 * Created by Skying on 2017/3/6.
 */
public interface SearchService {

    SearchResult search(String queryString, int page, int rows);

}
