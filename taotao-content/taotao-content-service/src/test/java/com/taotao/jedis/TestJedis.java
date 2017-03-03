package com.taotao.jedis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Skying on 2017/3/3.
 */
public class TestJedis {

    @Test
    public void testJedis() throws Exception {
        // 1.创建一个jedis对象，需要制定服务的ip和端口号
        Jedis jedis = new Jedis("192.168.56.2", 6379);
        // 2.直接操作Jedis 服务器
        jedis.set("jedis-key", "1234");
        String result = jedis.get("jedis-key");
        System.out.println(result);
        // 3.关闭jedis
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception {
        // 1.穿件一个连接池对象，需要指定服务的ip和端口号
        // 连接池对象是单例
        JedisPool jedisPool = new JedisPool("192.168.56.2", 6379);
        // 2.从连接池获得数据
        Jedis jedis = jedisPool.getResource();
        // 3.使用Jedis操作数据库(方法级别使用)
        String result = jedis.get("jedis-key");
        System.out.println(result);
        // 4.一定要关闭Jedis连接
        jedis.close();
        // 5.系统关闭前关闭连接池
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception {
        // 1.创建一个JedisCluster 对象，构造参数Set类型，个集合中每个元素是HostAndPort类型
        Set<HostAndPort> nodes = new HashSet<>();
        // 向集合中添加节点
        nodes.add(new HostAndPort("192.168.56.2",7001));
        nodes.add(new HostAndPort("192.168.56.2",7002));
        nodes.add(new HostAndPort("192.168.56.2",7003));
        nodes.add(new HostAndPort("192.168.56.2",7004));
        nodes.add(new HostAndPort("192.168.56.2",7005));
        nodes.add(new HostAndPort("192.168.56.2",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        // 2.直接使用JedisCluster 来操作redis, 自带连接池。jedisCluster 对象可以是单例的。
        jedisCluster.set("cluster-test", "hello jedis Cluster");
        String result = jedisCluster.get("cluster-test");
        // 3.系统关闭前关闭JedisCluster
        jedisCluster.close();
    }
}
