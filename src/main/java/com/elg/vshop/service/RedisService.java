package com.elg.vshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Set;

@Service
public class RedisService {
    @Value("${jwt.access.token.expiration}")
    private int accessTokenExpirationTime;

    private final JedisPool pool;

    RedisService() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        pool = new JedisPool(poolConfig, "localhost", 6379);
    }

    public void sadd(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sadd(key, value);
            jedis.expire(key, accessTokenExpirationTime);
        }
    }

    public void srem(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.srem(key, value);
        }
    }

    public boolean sismember(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.sismember(key, value);
        } catch (JedisException e) {
            return true;
        }
    }

    public Set<String> sysMembers(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.smembers(key);
        }
    }
}
