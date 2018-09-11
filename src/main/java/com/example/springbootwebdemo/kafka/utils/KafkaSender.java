package com.example.springbootwebdemo.kafka.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootwebdemo.kafka.domain.Message;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Component
public class KafkaSender{

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    //发送消息方法
    public void send() {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        System.out.println("message="+ JSONObject.toJSONString(message));
        //topic在发送时会自动进行创建
        kafkaTemplate.send("kafka1",JSONObject.toJSONString(message));
        ProducerRecord msg=new ProducerRecord("hahah",1,1,"gsgsgg");
        kafkaTemplate.send(msg);
    }
}
