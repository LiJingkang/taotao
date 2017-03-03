package com.taotao.jedis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

/**
 * Created by Skying on 2017/3/3.
 */
public class TestJedis {

    @Test
    public void testJedis() throws Exception {
        // 1.创建一个jedis对象，需要制定服务的ip和端口号
        Jedis jedis= new Jedis("192.168.56.2", 6379);
        // 2.直接操作数据库
        jedis.set("jedis-key", "1234");
        String result = jedis.get("jedis-key");
        System.out.println(result);
        // 3.关闭jedis
        jedis.close();
    }
}
