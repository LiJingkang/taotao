package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 商品管理Controller
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    // 从路径里面把参数取出来，如果名字和参数一样的话就不需要使用@PathVariable的属性
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId) ;
        return tbItem;

    }

    // 返回分页的查询结果
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping("/item/Skying")
    @ResponseBody
    public String getId(){
        return "Skying";
    }

    // 使用POST 方法
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem item, String desc) {
        TaotaoResult result = itemService.addItem(item, desc);
        return result ;
    }
}
