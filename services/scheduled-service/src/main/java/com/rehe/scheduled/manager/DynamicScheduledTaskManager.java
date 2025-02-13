package com.rehe.scheduled.manager;

import com.rehe.common.redis.DistributedLock;
import com.rehe.scheduled.modules.task.dto.ScheduledTaskDto;
import com.rehe.scheduled.modules.task.service.ScheduledTaskService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author xiech
 * @description
 * @date 2025/2/11
 */
@Component
@RequiredArgsConstructor
public class DynamicScheduledTaskManager {
    private static final Logger log = LoggerFactory.getLogger(DynamicScheduledTaskManager.class);

    @Getter
    private static DynamicScheduledTaskManager instance;

    private final TaskScheduler taskScheduler;
    private final ScheduledTaskService scheduledTaskService;
    private final ApplicationContext applicationContext;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @PostConstruct
    private void init(){
        instance = this;
        reloadTask();
    }

    public void reloadTask(){
        List<ScheduledTaskDto> scheduledTaskList = scheduledTaskService.findTaskByStatus(1);
        scheduledTaskList.forEach(this::scheduleTask);
    }
    public void scheduleTask(ScheduledTaskDto task) {
        stopTask(task.getTaskId());
        ScheduledFuture<?> future = null;
        switch (task.getTaskMode()) {
            case 1:
                future = taskScheduler.schedule(() -> executeTask(task), new CronTrigger(task.getExpression()));
                log.info("📌 任务 [{}] 采用 Cron 方式调度，表达式：{}", task.getTaskId(), task.getExpression());
                break;
            case 2:
                future = taskScheduler.scheduleAtFixedRate(
                        () -> executeTask(task),
                        Instant.now(),
                        Duration.ofMillis(Long.parseLong(task.getExpression()))
                );
                log.info("📌 任务 [{}] 采用固定间隔调度，每 {} 毫秒执行一次", task.getTaskId(), task.getExpression());
                break;
            case 3:
                future = taskScheduler.scheduleWithFixedDelay(
                        () -> executeTask(task),
                        Duration.ofMillis(Long.parseLong(task.getExpression()))
                );
                log.info("📌 任务 [{}] 采用固定延迟调度，每次任务完成后等待 {} 毫秒再执行", task.getTaskId(), task.getExpression());
                break;
            default:
                log.warn("⚠️ 任务 [{}] 调度失败，未识别的任务模式：{}", task.getTaskId(), task.getTaskMode());
                return;
        }

        scheduledTasks.put(task.getTaskId(), future);
        log.info("📌 任务 [{}] 调度成功，模式：{}，Cron/间隔：{}", task.getTaskId(), task.getTaskMode(), task.getExpression());
    }

    public void stopTask(String taskId) {
        ScheduledFuture<?> future = scheduledTasks.get(taskId);
        if (future != null) {
            future.cancel(false);
            scheduledTasks.remove(taskId);
            log.info("⏸️ 任务 [{}] 已停止", taskId);
        }
    }


    public void executeTask(String taskId){
        ScheduledTaskDto task = scheduledTaskService.findTaskByTaskId(taskId);
        if(task != null){
            executeTask(task);
        }
    }

    private void executeTask(ScheduledTaskDto task){
        log.info("🚀 尝试执行任务 [{}]...", task.getTaskId());

        String lockKey = "task_lock:" + task.getTaskId();
        RLock lock = DistributedLock.getInstance().tryLock(lockKey, 5, 120); // 20秒等待，120秒锁定
        if (lock == null) {
            log.warn("⚠️ 任务 [{}] 获取锁失败，可能已在执行中", task.getTaskId());
            return;
        }
        try {
            Object bean = applicationContext.getBean("taskService");
            Method method = bean.getClass().getMethod(task.getMethodName());
            method.invoke(bean);
            log.info("✅ 任务 [{}] 执行完成", task.getTaskId());
        } catch (Exception e) {
            log.error("❌ 任务 [{}] 执行失败：{}", task.getTaskId(), e.getMessage());
        } finally {
            DistributedLock.getInstance().unlock(lock);
        }
    }

}
