package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchItemService;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 搜索服务功能实现
 *
 * Created by Skying on 2017/3/6.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, int page, int rows) {
        // 根据查询条件拼装查询对象

        // 创建一个SorQuery对象

        // 设置查询条件

        // 设置分页条件

        // 设置默认搜索域

        // 设置高亮显示

        // 调用dao执行查询

        // 计算查询结果的总页数

        return null;
    }
}
