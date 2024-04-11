package com.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;

public class CustomProducerTransactions {
    public static void main(String[] args) {
        //0.属性配置
        Properties properties = new Properties();

        //连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.231.128:9094");

        //properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.128:9093");

        //指定key和value序列化类型(必须配置)
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        //指定事务id(必须全局唯一)
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, new Date().toString());

        //1.创建生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        kafkaProducer.initTransactions();

        kafkaProducer.beginTransaction();

        try{
            String a = null;
            //2.发送数据
            for (int i = 0; i < 5; i++) {
                kafkaProducer.send(new ProducerRecord<>("test","test trans"+i));

                if(i == 3){
                    System.out.println(a.hashCode());
                }
            }

        }catch (Exception e){
            System.out.println("发生异常。。。");
            kafkaProducer.abortTransaction();
        }finally {
            //3.关闭资源
            kafkaProducer.close();
        }
    }
}
