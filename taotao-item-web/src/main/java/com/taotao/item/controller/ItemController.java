package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 商品详情页面展示Controller
 *
 * Created by Skying on 2017/3/9.
 */
@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        // 取商品详细信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        // 取商品详情
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        // 把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        // 返回逻辑视图
        return "item";
    }
}
