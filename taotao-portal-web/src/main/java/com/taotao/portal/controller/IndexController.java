package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Skying on 2017/3/2.
 */

@Controller
public class IndexController {

    // 后台不拦截 / 只拦截 .html
    // 当寻找index.html的时候没有找到，会调用这个方法。
    @RequestMapping("/index")
    public String showIndex() {
        return "index";
    }
}
