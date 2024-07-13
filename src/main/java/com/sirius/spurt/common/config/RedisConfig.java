package com.sirius.spurt.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Object> tokenRedisTemplate() {
        RedisTemplate<String, Object> tokenRedisTemplate = new RedisTemplate<>();
        tokenRedisTemplate.setConnectionFactory(redisConnectionFactory());
        tokenRedisTemplate.setKeySerializer(new StringRedisSerializer());
        tokenRedisTemplate.setValueSerializer(new StringRedisSerializer());
        return tokenRedisTemplate;
    }
}
