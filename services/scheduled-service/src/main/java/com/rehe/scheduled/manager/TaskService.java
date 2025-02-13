package com.rehe.scheduled.manager;

import com.rehe.common.redis.RedisService;
import com.rehe.common.uuid.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xiech
 * @description
 * @date 2025/2/10
 */

@Component
@RequiredArgsConstructor
public class TaskService {
    private final RedisService redisService;
    private final SnowflakeIdGenerator snowflakeIdGenerator;


//    @Scheduled(fixedRate = 5000)
//    public  void i(){
//        System.out.println("time==="+redisService.getRedisTime());
//        System.out.println("定时任务执行：" + System.currentTimeMillis());
//        int i = 0;
////        while (i<10000){
////            i++;
////            System.out.println(snowflakeIdGenerator.nextId("ORD")); // 订单 ID
////        }
//
//
//    }


    public void test(){
        System.out.println("测试任务1定时任务执行：" + System.currentTimeMillis());
    }

    public void test2(){
        System.out.println("测试任务2定时任务执行：" + System.currentTimeMillis());
    }

    public void test3(){
        System.out.println("测试任务3定时任务执行：" + System.currentTimeMillis());
    }

    public void test4(){
        System.out.println("测试任务4定时任务执行：" + System.currentTimeMillis());
    }

    public void test5(){
        System.out.println("测试任务5定时任务执行：" + System.currentTimeMillis());
    }

}
