package com.example.springdataredis;

import com.alibaba.fastjson2.JSON;
import com.example.springdataredis.pojo.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
@SpringBootTest
class SpringDataRedisApplicationTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testString(){
        ValueOperations valueOps = redisTemplate.opsForValue();
        valueOps.set("name","小狗");
        String name = valueOps.get("name").toString();
        System.out.println(name);
    }

    @Test
    public void testSaveUser(){
        User user = User.builder()
                .age("18")
                .name("阿枣").build();
        ValueOperations valueOps = redisTemplate.opsForValue();
        valueOps.set("user", user);
        User user1 = (User)valueOps.get("user");
        System.out.println(user1);
    }

    @Test
    public void testStringTemplate(){
        User user = User.builder()
                .age("18")
                .name("阿枣").build();
        String userStr = JSON.toJSONString(user);

        ValueOperations valueOps = stringRedisTemplate.opsForValue();
        valueOps.set("user", userStr);
        String user1 = valueOps.get("user").toString();
        User parsedUser = JSON.parseObject(user1, User.class);
        System.out.println(parsedUser);
    }
}
