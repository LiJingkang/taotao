package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by Skying on 2017/3/5.
 */
public interface SearchItemMapper {

    // 手写 Mapper ，但是还是需要一个映射文件。
    List<SearchItem> getItemList();

}
