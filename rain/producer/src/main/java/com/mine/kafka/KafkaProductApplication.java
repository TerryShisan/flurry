package com.mine.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class KafkaProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProductApplication.class, args);
    }

//    @Autowired
//    private  KafkaProducer kafkaProducer;
////
//    @PostConstruct
//    public void product(){
//        this.kafkaProducer.produce();
//    }
}
