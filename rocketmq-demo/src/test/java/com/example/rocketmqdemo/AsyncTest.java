package com.example.rocketmqdemo;

import com.example.rocketmqdemo.constant.MqConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;

public class AsyncTest {
    @Test
    public void asyncProducer()throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("async-producer-group");
        producer.setNamesrvAddr(MqConstant.NAMESRV_ADDR);
        //不设置时报错：sendDefaultImpl call timeout
        producer.setSendMsgTimeout(20000);
        producer.start();

        Message message = new Message("asyncTopic","一条异步消息".getBytes());
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
                System.out.println("发送失败:"+throwable.getMessage());
            }
        });
        System.out.println("睡眠3s");
        Thread.sleep(30000);
        producer.shutdown();
    }
}
