package com.itheima.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息，主要用于生产者不关心发送结果
 *
 *
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("rocketmq-nameserver2:9876;rocketmq-nameserver1:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("base","tag1",("Hello World One Way"+(i+1)).getBytes());
            producer.sendOneway(msg);
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
