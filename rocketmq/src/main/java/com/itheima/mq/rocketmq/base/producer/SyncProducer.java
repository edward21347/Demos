package com.itheima.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 *
 * 1.创建producer，指定生产组名
 * 2.指定nameserver地址
 * 3.启动producer
 * 4.创建消息对象，指定消息主题、消息体
 * 5.发送消息
 * 6.关闭生产者
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("rocketmq-nameserver2:9876;rocketmq-nameserver1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("base","tag1",("Hello World"+i+1).getBytes());
            SendResult result = producer.send(msg);
            System.out.println("发送结果:" + result);
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
