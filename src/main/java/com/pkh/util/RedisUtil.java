package com.pkh.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtil {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置Redis键值对
     * set key
     *
     * @param key Redis键
     * @param value Redis值
     * @param <T>
     */
    public <T> void setCacheObject(final String key, final T value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
    }

    /**
     * 根据Redis键，获取Redis值
     * get key
     *
     * @param key Redis键
     * @return
     */
    public Object getCacheObject(final String key)
    {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 根据Redis键，自增1
     * incur key
     *
     * @param key Redis键
     * @return
     */
    public Long incur(final String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.increment(key);
    }

    /**
     * 根据Redis键，添加集合
     * sadd key value1, value2
     *
     * @param key Redis键
     * @param userId 集合中的元素（用户Id）
     * @return
     */
    public Long AddSet(final String key, final String userId) {
        SetOperations<String, Object> operations = redisTemplate.opsForSet();
        return operations.add(key, userId);
    }

    /**
     * 根据Redis键，统计集合数量
     * scard key
     *
     * @param key Redis键
     * @return
     */
    public Long CountSet(final String key) {
        SetOperations<String, Object> operations = redisTemplate.opsForSet();
        return operations.size(key);
    }

}
