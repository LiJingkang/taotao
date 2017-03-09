package com.taotao.activemq;

import com.alibaba.dubbo.config.ApplicationConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Skying on 2017/3/9.
 */
public class SpringActivemq {

    // 使用 jsmTemplate 发送消息
    @Test
    public void testJmslate() throws Exception {
        // 1. 初始化 spring 容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 2. 从容器中获得 JmsTemplate 对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        // 3. 从容器中获得 Destination 对象
        Destination destination = (Destination) applicationContext.getBean("test-queue");
        // 4. 发送消息
        jmsTemplate.send(destination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("spring activemq send queue message");
                return message;
            }
        });
    }
}
