package com.itheima.mq.rocketmq.filter.sql;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息：
 * 1.创建生产者，指定组名
 * 2.指定nameserver地址
 * 3.启动producer
 * 4.创建消息对象
 * 5.发送消息，设置回调
 * 6.关闭生产者
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("rocketmq-nameserver2:9876;rocketmq-nameserver1:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("base","tag2",("Hello World"+i+1).getBytes());
            msg.putUserProperty("i",String.valueOf(i));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功:"+sendResult);
                }
                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败："+throwable.getMessage());
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
