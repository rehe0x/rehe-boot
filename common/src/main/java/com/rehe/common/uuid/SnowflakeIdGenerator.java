package com.rehe.common.uuid;

import com.rehe.common.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author xiech
 * @description
 * @date 2025/2/12
 */
@Component
@RequiredArgsConstructor
public class SnowflakeIdGenerator {
    private static final long EPOCH = 1640995200000L; // 自定义起始时间（2022-01-01）
    private static final long MACHINE_ID = 1L; // 机器 ID
    private static final long MACHINE_BITS = 10L;
    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_SEQUENCE = (1 << SEQUENCE_BITS) - 1;
    private static final long MACHINE_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = MACHINE_BITS + SEQUENCE_BITS;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    private final RedisService redisService;

    /**
     * 生成带前缀的唯一 ID
     * @param prefix 业务前缀（如 ORD, USR, PRD）
     * @return 唯一 ID
     */
    public synchronized String nextId(String prefix) {
        long timestamp = redisService.getRedisTime();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨异常");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                while ((timestamp = redisService.getRedisTime()) <= lastTimestamp) {
                    // 忙等直到下一个毫秒
                }
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        long id = ((timestamp - EPOCH) << TIMESTAMP_SHIFT) | (MACHINE_ID << MACHINE_SHIFT) | sequence;
        return prefix + id;
    }
}
