package com.example.rocketmqdemo;

import com.example.rocketmqdemo.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TagTest {
    @Test
    public void tagProducer()throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("tag-producer-group");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //不设置时报错：sendDefaultImpl call timeout
        producer.setSendMsgTimeout(20000);
        producer.start();

        Message message = new Message("TagTopic", "vip1","hello world1".getBytes());
        producer.send(message);
        message = new Message("TagTopic", "vip2","hello world2".getBytes());
        producer.send(message);

        System.out.println("发送成功");
        producer.shutdown();
    }

    @Test
    public void tagConsumer1()throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tag-consumer-group1");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.subscribe("TagTopic","vip1");
        //多线程 并发模式
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("接收到消息:");
                System.out.println(new String(list.get(0).getBody()));
                //CONSUME_SUCCESS表示成功消费消息
                //RECONSUME_LATER表示消费失败，消息重新回到队列
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

    @Test
    public void tagConsumer2()throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tag-consumer-group2");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.subscribe("TagTopic","vip2");
        //多线程 并发模式
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("接收到消息:");
                System.out.println(new String(list.get(0).getBody()));
                //CONSUME_SUCCESS表示成功消费消息
                //RECONSUME_LATER表示消费失败，消息重新回到队列
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}