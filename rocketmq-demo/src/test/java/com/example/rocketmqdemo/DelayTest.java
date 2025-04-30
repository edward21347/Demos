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

import java.util.Date;
import java.util.List;

public class DelayTest {

    @Test
    public void delayProducer()throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("delay-producer-group");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //不设置时报错：sendDefaultImpl call timeout
        producer.setSendMsgTimeout(20000);
        producer.start();

        Message message = new Message("orderTopic", "订单号，座位号".getBytes());
        //消息设置延迟等级
        message.setDelayTimeLevel(3);

        producer.send(message);
        System.out.println("发送时间:"+new Date());
        producer.shutdown();
    }

    @Test
    public void consumer()throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("delay-consumer-group");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.subscribe("orderTopic","*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("收到消息:"+new Date()+"\n"+list.get(0).toString());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.in.read();
    }
}
