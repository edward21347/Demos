package com.itheima.mq.rocketmq.order;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;
public class OrderlyProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("worker1:9876;worker2:9876");
        producer.start();
        List<OrderStep> orders = OrderStep.buildOrders();
        for (int i = 0; i < orders.size(); i++) {
            final OrderStep order = orders.get(i);
            Message msg = new Message("OrderTopicTest","order_"+order.getOrderId(), JSONObject.toJSONString(order).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 使用顺序发送，基于订单ID选择队列
            producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    // 获取订单ID
                    Long orderId = ((OrderStep) arg).getOrderId();

                    // 将订单ID转换为数字，用于计算队列索引
                    // 使用 hashCode() 确保相同订单ID总是映射到同一个队列
                    int id = orderId.hashCode();
                    if (id < 0) {
                        id = Math.abs(id); // 确保索引为正数
                    }

                    // 计算队列索引：使用取模运算确保在队列数量范围内
                    int index = id % mqs.size();
                    //订单id 队列索引
                    System.out.println("订单ID:"+order.getOrderId()+" 队列索引:"+index);
                    // 返回对应的队列
                    return mqs.get(index);
                }
            }, order);
        }
        producer.shutdown();
    }
}
