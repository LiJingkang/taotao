package com.taotao.search.exception;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * Created by Skying on 2017/3/7.
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    // 创建一个 Logger 对象
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        logger.info("进入全局异常处理器。。。。。。");
        // 1. 控制台打印异常
        e.printStackTrace();
        // 2. 向日志文件中写入异常
        logger.error("系统发生异常", e);
        logger.debug("测试handler的类型",e);
        // 3. 发邮件 发短信
        // 使用 jmail 发送邮件。
        // 短信运营商
        // 4. 展示错误页面
        // 要返回一个逻辑视图，并且要传递参数。我们创建一个 Model
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "系统发生异常，请稍后重试，你的电脑有问题。");
        // 用到逻辑师徒 我们就不能写全路径。因为有视图解析器。 会自动加上前缀和后缀
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
