package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * 接收 Activemq 发送的消息
 *
 * Created by Skying on 2017/3/9.
 */
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // 接收消息

        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.print(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
