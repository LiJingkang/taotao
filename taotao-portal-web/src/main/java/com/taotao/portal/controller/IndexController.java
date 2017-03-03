package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skying on 2017/3/2.
 */

@Controller
public class IndexController {

    @Value("${AD1_CATEGORY_ID}")
    private long AD1_CATEGORY_ID;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @Autowired
    private ContentService contentService;


    // 后台不拦截 / 只拦截 .html
    // 当寻找index.html的时候没有找到，会调用这个方法。
    @RequestMapping("/index")
    public String showIndex(Model model) {
        // 1.根据cid查询轮播图图片列表
        List<TbContent> contentList = contentService.getContentByCid(AD1_CATEGORY_ID);
        // 2.把列表转化为Ad1Node列表
        List<AD1Node> ad1Nodes = new ArrayList<>();
        for (TbContent tbContent : contentList) {
            AD1Node node = new AD1Node();
            node.setAlt(tbContent.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setHref(tbContent.getUrl());
            // 添加到节点列表
            ad1Nodes.add(node);
        }
        // 3.把列表转化为json数据
        String ad1Json = JsonUtils.objectToJson(ad1Nodes);
        // 4.把json数据传递给页面
        // 这样页面就会接收到这个参数，然后返回罗技视图
        model.addAttribute("ad1", ad1Json);
        return "index";
    }
}
