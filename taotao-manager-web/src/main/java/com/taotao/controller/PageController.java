package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Skying on 2017/2/28.
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex() {
        return  "index";
    }
}
