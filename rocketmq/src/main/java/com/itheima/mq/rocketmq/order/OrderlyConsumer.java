package com.itheima.mq.rocketmq.order;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class OrderlyConsumer {
    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        consumer.setNamesrvAddr("worker1:9876;worker2:9876");
        consumer.subscribe("OrderTopicTest","*");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                // 设置为自动提交（顺序消费模式下，RocketMQ会确保在消费成功后提交offset）
                context.setAutoCommit(true);

                for (MessageExt msg : msgs) {
                    try {
                        String orderId = msg.getKeys();
                        // 解析消息体
                        String body = new String(msg.getBody(), "UTF-8");
                        OrderStep orderStep = JSONObject.parseObject(body, OrderStep.class);

                        System.out.println("orderId:"+orderId+" 线程:"+Thread.currentThread().getName()+" 订单:"+orderStep);

                        System.out.println("订单处理完毕！");
                    } catch (UnsupportedEncodingException e) {
                        System.err.printf("消费消息失败: %s, 异常: %s%n",
                                msg.getMsgId(), e.getMessage());

                        // 发生异常时，返回SUSPEND_CURRENT_QUEUE_A_MOMENT表示稍后重试
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
    }
}
