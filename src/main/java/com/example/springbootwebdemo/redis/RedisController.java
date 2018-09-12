package com.example.springbootwebdemo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@RequestMapping("redis")
//@RestController
public class RedisController {
//    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/strRestTemplate")
    public String strRestTemplate(){

        stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                return connection;
            }
        });
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("strRedis","StringRedisTemplate");
        System.out.println(valueOperations.get("strRedis"));
        return "StringRedisTemplate success!";
    }

    @RequestMapping("/operation")
    public String test(){
        //操作string,需要被序列化转换，建议使用StringRedisTemplate
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("redisstr","hah",1000, TimeUnit.HOURS);
        System.out.println(valueOperations.get("redisstr"));

        //List设置
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("redisList","redisList");
        Object redisList = listOperations.leftPop("redisList");
        System.out.println("----"+redisList);

        //redis set集合
        SetOperations setOperations = redisTemplate.opsForSet();
        Long add = setOperations.add("redisSet", "redisSet");
        System.out.println("----"+setOperations.pop("redisSet"));

        //redis hash
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.hasKey("hashRedis", "hashRedis");
        System.out.println("-------"+hashOperations.entries("hashRedis"));

        //redisTemplate.afterPropertiesSet();在配置文件设置完成之后使用
        //开启 事务
//        redisTemplate.multi();
//        redisTemplate.discard();

        List <RedisClientInfo> clientList = redisTemplate.getClientList();//获取连接redis数据库信息
        for (RedisClientInfo clientInfo:clientList ){
            System.out.printf("--"+clientInfo.toString()+"events="+clientInfo.getEvents()+"\n"
            +"port="+clientInfo.getAddressPort()+"name="+clientInfo.getName()+clientInfo.getFileDescriptor()+
                    clientInfo.getFlags()+clientInfo.getLastCommand()+clientInfo.getAge()+clientInfo.getBufferLength()+
                    clientInfo.getDatabaseId());
        }
        return "test";

    }
}
