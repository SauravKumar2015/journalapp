package com.saurav.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saurav.journalApp.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> entityClass) {
      try {
          Object o = redisTemplate.opsForValue().get(key);
          ObjectMapper mapper = new ObjectMapper();
          return mapper.readValue(o.toString(), entityClass);
      } catch (Exception e) {
          log.error("Redis get error for key {}: {}", key, e.getMessage());
          return null;
      }
    }


    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }
}
