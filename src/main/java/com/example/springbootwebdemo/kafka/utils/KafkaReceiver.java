package com.example.springbootwebdemo.kafka.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Optional;

//需要修改server.properties的listener主机地址不然Java获取不到消息。
//listeners=PLAINTEXT://192.168.232.128:9092

@Component
public class KafkaReceiver {

    @KafkaListener(topics = {"kafka1"})
    public void receiver(ConsumerRecord<?,?> record){
        Optional<?> value = Optional.ofNullable(record.value());
        if (value.isPresent()){
            Object o = value.get();
            System.out.println("------record"+record);
            System.out.println("-------message="+value);
        }
    }

}
