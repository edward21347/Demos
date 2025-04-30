package com.itheima.mq.rocketmq.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Producer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("rocketmq-nameserver2:9876;rocketmq-nameserver1:9876");
        producer.start();

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Message msg = new Message("base","tag1",("Hello World"+i+1).getBytes());
            messages.add(msg);
        }
        SendResult result = producer.send(messages);
        System.out.println("发送结果:" + result);
        TimeUnit.SECONDS.sleep(1);

        producer.shutdown();
    }
}
