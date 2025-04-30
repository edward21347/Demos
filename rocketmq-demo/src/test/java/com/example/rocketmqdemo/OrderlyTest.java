package com.example.rocketmqdemo;

import com.example.rocketmqdemo.constant.MqConstant;
import com.example.rocketmqdemo.domain.MsgModel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class OrderlyTest {
    private List<MsgModel> msgModels = Arrays.asList(
            new MsgModel("qwer", 1, "下单"),
            new MsgModel("qwer", 1, "短信"),
            new MsgModel("qwer", 1, "物流"),
            new MsgModel("zxcv", 2, "下单"),
            new MsgModel("zxcv", 2, "短信"),
            new MsgModel("zxcv", 2, "物流")
    );
    @Test
    void orderlyProducer() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("orderly-producer-group");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //不设置时报错：sendDefaultImpl call timeout
        producer.setSendMsgTimeout(20000);
        producer.start();

        msgModels.forEach(msgModel -> {
            Message message = new Message("OrderlyTopic", msgModel.toString().getBytes());

            try {
                //根据业务标识，相同业务标识（此处为订单号）去同一个队列（局部有序）
                producer.send(message, new MessageQueueSelector() {
                    //send的第三个参数msgModel.getOrderSn()传入到select()的第三个参数Object arg
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                        //返回选择的队列
                        int hashCode = arg.toString().hashCode();
                        int i = hashCode % list.size();
                        return list.get(i);
                    }
                },msgModel.getOrderSn());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        System.out.println("发送完成");
        producer.shutdown();
    }

    @Test
    void orderlyConsumer()throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("orderly-consumer-group");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.subscribe("OrderlyTopic","*");
        //MessageListenerOrderly 顺序模式，每个队列一个线程
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                System.out.println("线程号:"+Thread.currentThread().getId()+" "+new String(list.get(0).getBody()));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
