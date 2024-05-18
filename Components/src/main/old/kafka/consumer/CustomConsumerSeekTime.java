package com.example.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

//指定时间进行消费
public class CustomConsumerSeekTime {
    public static void main(String[] args) {
        //0 配置
        Properties properties = new Properties();
        //连接
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.231.128:9093");

        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test6");
        //分区分配策略
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, StickyAssignor.class.getName());
        //创建生产者
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        //订阅主题
        List<String> topics = new ArrayList<>();
        topics.add("test2");
        kafkaConsumer.subscribe(topics);

        //获取分区信息
        Set<TopicPartition> assignment = kafkaConsumer.assignment();

        //消费者组初始化需要时间，若还未初始化好，则可能assignment中无信息，需要保证分区分配方案已经执行完毕
        while(assignment.size() == 0){
            kafkaConsumer.poll(Duration.ofSeconds(1));

            assignment = kafkaConsumer.assignment();
        }

        //时间转换为offset
        HashMap<TopicPartition, Long> topicPartitionLongHashMap = new HashMap<>();

        //消费一天前的数据
        for (TopicPartition topicPartition : assignment) {
            topicPartitionLongHashMap.put(topicPartition, System.currentTimeMillis() - 1*24*3600*1000);
        }

        Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = kafkaConsumer.offsetsForTimes(topicPartitionLongHashMap);


        //指定消费offset
        for (TopicPartition topicPartition : assignment) {
            OffsetAndTimestamp offsetAndTimestamp = topicPartitionOffsetAndTimestampMap.get(topicPartition);
            kafkaConsumer.seek(topicPartition, offsetAndTimestamp.offset());
        }


        //消费数据
        while (true){
                                                                                    //拉取间隔时间
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));

            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);
            }

        }
    }



}
