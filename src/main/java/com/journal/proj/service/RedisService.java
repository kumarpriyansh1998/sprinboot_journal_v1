package com.journal.proj.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.journal.proj.api.response.quoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String category, Object o,Long ttl) {
        try{
            ObjectMapper  mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(category,jsonString,ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception e");
        }
    }

    public <T> T get(String category, Class<T> entityclass) {
        try{

            Object o = redisTemplate.opsForValue().get(category);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(),entityclass);
        }catch (Exception e){
            log.error("Exception: "+e);
            return null;
        }
    }
}
