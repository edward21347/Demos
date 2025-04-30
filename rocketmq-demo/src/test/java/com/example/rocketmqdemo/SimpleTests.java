package com.example.rocketmqdemo;

import com.example.rocketmqdemo.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SimpleTests {
    /**
     * 1.创建
     *  创建生产者，构造函数中指定生产者组
     *  指定namesrv_addr
     *  执行start函数启动生产者
     * 2.发送消息
     *  创建消息，构造函数中指定topic与内容
     *  发送消息
     *  接收发送结果
     * 3.结束
     *  执行shutdown方法结束生产者
     */
    @Test
    void simpleProducer() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //不设置时报错：sendDefaultImpl call timeout
        producer.setSendMsgTimeout(20000);
        producer.start();

        Message message = new Message("TopicA", "hello world".getBytes());
        SendResult sendResult = producer.send(message);
        System.out.println("发送结果:"+sendResult.getSendStatus());

        producer.shutdown();
    }


    /**
     * 1.创建
     *  创建消费者，指定消费者组
     *  指定namesrv地址
     *  订阅主题
     * 2.消费
     *  调用registerMessageListener注册监听器，实现consume方法
     *  调用start启动消费者
     */
    @Test
    void simpleConsumer()throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-consumer-group");
        consumer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        consumer.subscribe("TopicA","*");
        //多线程 并发模式
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("接收到消息:");
                System.out.println(list.get(0).toString());
                System.out.println(new String(list.get(0).getBody()));
                System.out.println("消息上下文:"+consumeConcurrentlyContext);
                //CONSUME_SUCCESS表示成功消费消息
                //RECONSUME_LATER表示消费失败，消息重新回到队列
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
