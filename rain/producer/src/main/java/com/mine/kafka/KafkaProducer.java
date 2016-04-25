package com.mine.kafka;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.module.annotation.PollableSource;
import org.springframework.cloud.stream.module.trigger.TriggerConfiguration;
import org.springframework.context.annotation.Import;

import javax.xml.crypto.Data;
import java.util.Date;

@EnableBinding(Source.class)
@Import(TriggerConfiguration.class)
public class KafkaProducer {

//    public final static String TOPIC = "test";
//    private final Producer<String, String> producer;

    public KafkaProducer() {
//        Properties props = new Properties();
//        /**
//         * 此处配置的是kafka的端口
//         */
//        props.put("metadata.broker.list", "127.0.0.1:9092");
//
//        /**
//         *  配置value的序列化类
//         */
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        /**
//         * 配置key的序列化类
//         */
//        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
//
//        props.put("request.required.acks", "-1");
//        producer = new Producer<>(new ProducerConfig(props));
    }


    @PollableSource
    public String produce() {
        Date d = new Date();
        String data = "{\"time\":\"zhangsan "+d.toString()+"\"}";

        System.out.println(data);
//        producer.send(new KeyedMessage<>(TOPIC, "prod", data));
        return data;
    }
}