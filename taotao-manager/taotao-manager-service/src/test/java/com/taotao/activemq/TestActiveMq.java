package com.taotao.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;
import java.net.ContentHandler;

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
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.56.2:61616");
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
        TextMessage textMessage = session.createTextMessage("hello activemq11wfeg11");
        // 8. 发送消息
        producer.send(textMessage);
        // 9. 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception {
        // 1. 创建一个连接工厂对象
        String ip = "tcp://192.168.56.2:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ip);
        // 2. 使用连接工厂对象创建一个连接
        Connection connection= connectionFactory.createConnection();
        // 3. 开启连接
        connection.start();
        // 4. 使用连接对象创建一个 Session 对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5. 使用 Session 创建一个 Destination,Destination 应该和消息的发送端一致
        Queue queue = session.createQueue("test-queue");
        // 6. 使用 Session 创建一个 Consumer 对象
        MessageConsumer consumer = session.createConsumer(queue);
        // 7. 向 Consumer 对象中设置一个 MessageListener 对象，用来接受消息
        // 匿名内部类
        consumer.setMessageListener(new MessageListener() {
            // 相当于写了这个接口的实现类  并创建一个实现类的对象
            @Override
            // 当消息到达的时候，调用这个方法
            public void onMessage(Message message) {
                // 8. 取消息内容
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        // 9. 打印消息内容
                        System.out.print(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                }
            }
        });
        // 系统等待接受消息
//        while (true) {
//            Thread.sleep(100);
//        }
        // 等待键盘输入 相当于等待
        System.in.read();
        // 10. 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    //topic
    //Producer
    @Test
    public void testTopicProducer() throws Exception {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.56.2:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建Destination，应该使用topic
        Topic topic = session.createTopic("test-topic");
        //创建一个Producer对象
        MessageProducer producer = session.createProducer(topic);
        //创建一个TextMessage对象
        TextMessage textMessage = session.createTextMessage("hello activemq topic");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumser() throws Exception {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.56.2:61616");
        //使用连接工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用Session创建一个Destination，Destination应该和消息的发送端一致。
        Topic topic = session.createTopic("test-topic");
        //使用Session创建一个Consumer对象
        MessageConsumer consumer = session.createConsumer(topic);
        //向Consumer对象中设置一个MessageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                //取消息的内容
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        //系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
        System.out.println("topic消费者3.。。。");
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
