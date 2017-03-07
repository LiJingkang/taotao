package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;
import com.taotao.search.service.SearchService;
import org.quartz.core.QuartzSchedulerResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 搜索服务Controller
 *
 * Created by Skying on 2017/3/7.
 */
@Controller
public class SearchController {

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception{

        // 调用服务执行查询
        // 将 rows 的数据放在配置文件里面
        // 把查询条件进行转码，解决get乱码问题
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        SearchResult searchResult = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
        // 把结果传递给页面
        // 需要一个 Model 将这些数据回显
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);

        // 返回逻辑视图
        return "search";
    }
}
