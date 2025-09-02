package com.hmdm.util;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * A service used for running standalone tasks in the background.
 */
@Slf4j
@Service
public class BackgroundTaskRunnerService {

    /**
     * Executor for the tasks to be executed in the background.
     */
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    /**
     * Executor for the repeatable tasks to be executed in the background.
     */
    private final ScheduledThreadPoolExecutor scheduledExecutor =
            (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);

    /**
     * Submits the specified task for execution in a background thread.
     *
     * @param task the task to execute
     */
    public void submitTask(Runnable task) {
        log.debug("Submitting task for execution: {}. Executor state: active tasks: {}, total tasks: {}, queue size: {}",
                task, executor.getActiveCount(), executor.getTaskCount(), executor.getQueue().size());
        executor.submit(task);
    }

    /**
     * Submits a repeatable task for execution in a background thread at fixed intervals.
     *
     * @param task         the task to execute
     * @param initialDelay the delay before first execution
     * @param period       the interval between executions
     * @param unit         the time unit for delay and period
     * @return a Future representing the task
     */
    public Future<?> submitRepeatableTask(Runnable task, long initialDelay, long period, TimeUnit unit) {
        log.debug("Submitting repeatable task: {}. Executor state: active tasks: {}, total tasks: {}, queue size: {}",
                task, executor.getActiveCount(), executor.getTaskCount(), executor.getQueue().size());
        return scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * Graceful shutdown of executors when Spring context is destroyed.
     */
    @PreDestroy
    public void shutdownExecutors() {
        log.info("Shutting down background task executors.");
        executor.shutdown();
        scheduledExecutor.shutdown();
    }
}
