package com.rehe.common.redis;

import com.rehe.common.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author xiech
 * @description
 * @date 2024/8/16
 */
@Component
public class DistributedLock {

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;
    @Value("${spring.data.redis.port:6379}")
    private int redisPort;
    @Value("${spring.data.redis.database:0}")
    private int redisDatabase;
    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    // 获取单例实例的方法
    @Getter
    private static DistributedLock INSTANCE;

    private RedissonClient redissonClient;

    // 私有构造函数，防止外部实例化
    private DistributedLock() {
    }

    @PostConstruct
    private void init() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setDatabase(redisDatabase);
//                .setPassword(redisPassword);
        this.redissonClient = Redisson.create(config);
        INSTANCE = this;
    }


    public <T> T lock(String lockKey,Callable<T> task) {
         return invokeAfterLockAcquire(lockKey,5,
                60, task,null);
    }

    public void lock(String lockKey,Runnable task) {
         invokeAfterLockAcquire(lockKey,5,
                60, ()->{
                     task.run();
                    return true;
                },null);
    }

    /**
     * 尝试获取锁并执行业务逻辑
     *
     * @param lockKey 锁的名称
     * @param waitTime 获取锁的最大等待时间
     * @param leaseTime 锁的持有时间
     * @param task 获取锁后需要执行的任务，返回值类型由泛型参数指定
     * @param onLockFailure 获取锁失败后需要执行的任务，返回值类型由泛型参数指定
     * @param <T> 返回值的类型
     * @return 返回任务执行的结果，或者锁获取失败后的结果
     */
    private  <T> T invokeAfterLockAcquire(
            String lockKey,
            long waitTime,
            long leaseTime,
            Callable<T> task,
            Callable<T> onLockFailure) {

        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        try {
            // 尝试获取锁
            isLocked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (isLocked) {
                // 锁获取成功后执行任务并返回结果
                return task.call();
            } else {
                // 锁获取失败后执行失败逻辑并返回结果
                if (onLockFailure != null) {
                    return onLockFailure.call();
                }
                throw new BusinessException("try lock failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("try lock failed");
        } finally {
            // 释放锁
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
