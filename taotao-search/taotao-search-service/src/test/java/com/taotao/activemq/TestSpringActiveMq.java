package com.taotao.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Skying on 2017/3/9.
 */
public class TestSpringActiveMq {

    @Test
    // 给一个 MessageListener 配置一下就可以了
    public void testSpringActiveMq() throws Exception {

        // 初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 等待
        System.in.read();
    }
}
