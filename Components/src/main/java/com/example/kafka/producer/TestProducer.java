package com.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class TestProducer {
    public static void main(String[] args) {
        //异步发送
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.8.128:9093");
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++){
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();

        //同步发送
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "172.16.8.128:9093");
//        props.put("transactional.id", "my-transactional-id");
//        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
//
//        producer.initTransactions();
//
//        try {
//            producer.beginTransaction();
//            for (int i = 0; i < 10; i++){
//                producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
//            }
//            producer.commitTransaction();
//        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
//            // We can't recover from these exceptions, so our only option is to close the producer and exit.
//            producer.close();
//        } catch (KafkaException e) {
//            // For all other exceptions, just abort the transaction and try again.
//            producer.abortTransaction();
//        }
//        producer.close();
    }
}
