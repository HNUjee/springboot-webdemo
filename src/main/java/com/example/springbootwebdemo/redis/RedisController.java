package com.example.springbootwebdemo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Iterator;
import java.util.List;

@Controller
public class RedisController {
    @Autowired
    StringRedisTemplate template;
    @Autowired
    RedisConnectionFactory factory;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/redis")
    @ResponseBody
    public void redis(){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("session","tom");
        listOperations.leftPush("session","bob");
        listOperations.leftPush("session","ka");
        List sessions = listOperations.range("session", 0, listOperations.size("session"));
        Iterator iterator =  sessions.iterator();
        while (iterator.hasNext()){
            System.out.println("result"+iterator.next());
        }

        System.out.printf("result="+template.hasKey("test"));
    }

    public static void main(String [] args){
        //连接本地的 Redis 服务
      /*  Jedis jedis = new Jedis("47.93.20.45",6379);
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        System.out.println("----------------------------------------");
        //存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }
        System.out.println("----------------------------------------");
        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }*/
    }
}
