package com.taotao.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by Skying on 2017/3/9.
 */
public class TestActiveMq {

    // queue
    // Producer
    @Test
    public void testQueueProducer() throws Exception {
        // 1. 创建一个连接工厂对象 ConnectionFactory对象。需要指定 mq 服务的ip 及端口号
        // 消息服务的端口是 61616
        String ip = "tcp://192.168.56.2:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ip);
        // 2. 使用ConnectionFactory 创建一个连接 Connection对象
        Connection connection = connectionFactory.createConnection();
        // 3. 开启连接。调用 Connetion 对象的 start 对象
        connection.start();
        // 4. 使用 Connection对象 创建一个 Session 对象
        // 第一个参数是是否开启事务。  分布式事务，不同的数据库里面。 一般不使用事务
        // 比如生成订单，插入数据以后。  使用消息队列，给其他地方发送消息。
        // 保证数据的最终一致，可以使用消息队列来实现。
        // 如果第一个参数为 true,第二个参数自动忽略。 如果不开始事务 flase, 第二个参数为消息的应答模式。一般自动应答就可以。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5. 使用 Session 对象创建一个 Destination 对象， 两种形式， queue topic 使用 queue
        // 参数就是消息队列的名称
        Queue queue = session.createQueue("test-queue");
        // 6. 使用 Session 对象创建一个 Producer 对象
        MessageProducer producer = session.createProducer(queue);
        // 7. 创建一个 TestMessage 对象
        /* TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("hello activemq"); */
        TextMessage textMessage = session.createTextMessage("hello activemq");
        // 8. 发送消息
        producer.send(textMessage);
        // 9. 关闭资源
        producer.close();
        session.close();
        connection.close();
    }
}
