package com.rehe.common.redis;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiech
 * @description
 * @date 2025/2/12
 */
@Component
@RequiredArgsConstructor
public class RedisService {

    private final  RedisTemplate<String, Object> redisTemplate;

//////////////////////////// 通用 Key 操作 ////////////////////////////

    /**
     * 设置 Key 的过期时间
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获取 Key 的剩余生存时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 判断 Key 是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除 Key
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    //////////////////////////// 字符串操作 ////////////////////////////

    /**
     * 设置字符串值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置字符串值并指定过期时间
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取字符串值
     */
    public <T> T get(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        return (T) ops.get(key);
    }

    //////////////////////////// 哈希操作 ////////////////////////////

    /**
     * 设置哈希字段值
     */
    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 批量设置哈希字段
     */
    public void hSetAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取哈希字段值
     */
    public <T> T hGet(String key, String hashKey) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        return (T) ops.get(key, hashKey);
    }

    /**
     * 获取整个哈希表
     */
    public <T> Map<String, T> hGetAll(String key) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        return (Map<String, T>) ops.entries(key);
    }

    //////////////////////////// 列表操作 ////////////////////////////

    /**
     * 向列表左侧添加元素
     */
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取列表范围内的元素
     */
    public <T> List<T> lRange(String key, long start, long end) {
        ListOperations<String, Object> ops = redisTemplate.opsForList();
        return (List<T>) ops.range(key, start, end);
    }

    //////////////////////////// 集合操作 ////////////////////////////

    /**
     * 向集合添加元素
     */
    public void sAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取集合所有成员
     */
    public <T> Set<T> sMembers(String key) {
        SetOperations<String, Object> ops = redisTemplate.opsForSet();
        return (Set<T>) ops.members(key);
    }

    //////////////////////////// 有序集合操作 ////////////////////////////

    /**
     * 向有序集合添加元素（带分数）
     */
    public void zAdd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取有序集合范围内的元素（按分数排序）
     */
    public <T> Set<T> zRange(String key, long start, long end) {
        ZSetOperations<String, Object> ops = redisTemplate.opsForZSet();
        return (Set<T>) ops.range(key, start, end);
    }

    //////////////////////////// 发布订阅 ////////////////////////////

    /**
     * 发布消息到频道
     */
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

    public long getRedisTime() {
        return Optional.ofNullable(redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.serverCommands().time()
        )).orElse(System.currentTimeMillis());
    }
}
