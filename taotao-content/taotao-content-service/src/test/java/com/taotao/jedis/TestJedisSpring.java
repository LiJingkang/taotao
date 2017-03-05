package com.taotao.jedis;

import javafx.application.Application;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Skying on 2017/3/5.
 */
public class TestJedisSpring {

    @Test
    public void testJedisClientPool() throws Exception {
        // 1.初始化spring 容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        // 2.从容器中获得JedisClinent对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        // 3.使用Jedisclient对象操作redis
        jedisClient.set("jedisclient", "mytest");
        String result = jedisClient.get("jedisclient");
        System.out.print(result);
    }
}
