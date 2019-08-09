package com.qf.orders.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/redis")
@RestController
public class RedisController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/set")
    public Object setStr() {
        // 操作字符串
        // redisTemplate.opsForValue()
        // 操作列表的方法
//        redisTemplate.opsForList()
        // 操作hash的方法
//        redisTemplate.opsForHash()
//        操作集合
//        redisTemplate.opsForSet()
        // 操作有序集合
//        redisTemplate.opsForZSet()

        redisTemplate.opsForValue().set("name", "张三");
        return "AAA";
    }

    @RequestMapping("/get")
    public Object getStr() {
        // 操作字符串
        // redisTemplate.opsForValue()
        // 操作列表的方法
//        redisTemplate.opsForList()
        // 操作hash的方法
//        redisTemplate.opsForHash()
//        操作集合
//        redisTemplate.opsForSet()
        // 操作有序集合
//        redisTemplate.opsForZSet()

        return redisTemplate.opsForValue().get("name");
    }
}
