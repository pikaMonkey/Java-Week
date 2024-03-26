package com.pkh.service.impl;

import com.pkh.service.StockService;
import com.pkh.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Resource
    RedisUtil redisUtil;

    @Resource
    RedissonClient redissonClient;

    private static final String LOCK_KEY = "stock_lock";

    @Override
    public Boolean deductStock(String productId, int count) {
        String lockKey = "stock_lock:" + productId;
        String requestId = UUID.randomUUID().toString();
        try {
            // 尝试上锁（分布式锁）
            boolean isLockAcquired = redisUtil.tryGetDistributedLock(lockKey, requestId, RedisUtil.EXPIRE_TIME);
            if (!isLockAcquired) {
                // 上锁失败，可以选择重试或者直接返回失败
                throw new RuntimeException("Failed to acquire distributed lock for product: " + productId);
            }

            // 上锁成功，执行扣减库存操作
            log.info("执行扣减库存逻辑");

        } finally {
            // 释放分布式锁
            redisUtil.releaseDistributedLock(lockKey, requestId);
        }
        return true;
    }

    @Override
    public Boolean deductStockWithRedisson(String productId, int count) {
        RLock lock = redissonClient.getLock(LOCK_KEY + productId);
        try {
            // 尝试上锁（分布式锁）
            lock.lock();
            // 上锁成功，执行扣减库存操作
            log.info("执行扣减库存逻辑");
            return true;
        } finally {
            // 释放分布式锁
            lock.unlock();
        }
    }
}
