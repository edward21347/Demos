package com.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducerParameters {
    public static void main(String[] args) {
        //0.属性配置
        Properties properties = new Properties();

        //连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.231.128:9093");
        //指定key和value序列化类型(必须配置)
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //提升吞吐量参数配置：
            //1.缓冲区大小(32mb)
            properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
            //2.批次大小(16K)
            properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
            //3.linger.ms(10ms)
            properties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
            //数据压缩
            properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        //1.创建生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        //2.发送数据
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send(new ProducerRecord<>("test","hello kafka"+i));
        }

        //3.关闭资源
        kafkaProducer.close();
    }
}
