package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * Created by Skying on 2017/2/27.
 */
public interface ItemService {

    // 逆向工程生成的接口。不需要实现类，直接使用Mapper文件来调用。
    TbItem getItemById(long itemId);

    // 分页查询的接口
    EasyUIDataGridResult getItemList(int page, int rows);

    TaotaoResult addItem(TbItem item, String desc);

    TbItemDesc getItemDescById(long itemId);

}
