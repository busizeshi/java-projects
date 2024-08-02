package com.itheima.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class TestStock {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testPassword() {
        String password = passwordEncoder.encode("123456");
        System.out.println(passwordEncoder.matches("123456", password));
    }

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("name", "itcast");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
}
