package com.itheima.mq.rocketmq.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 1.创建consumer，指定组名
 * 2.指定nameserver地址
 * 3.订阅主题topic和tag
 * 3.1 设置消费模式：负载均衡/广播，默认为负载均衡
 * 4.设置回调函数，处理消息
 * 5.启动消费者
 *
 */
public class Consumer {

    public static void main(String[] args) throws Exception{
        //1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定Nameserver地址
        consumer.setNamesrvAddr("rocketmq-nameserver2:9876;rocketmq-nameserver1:9876");
        //3.订阅主题Topic和Tag
        consumer.subscribe("TransactionTopic", "*");


        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            //接受消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "," + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动消费者consumer
        consumer.start();
        System.out.println("生产者启动");
    }
}
