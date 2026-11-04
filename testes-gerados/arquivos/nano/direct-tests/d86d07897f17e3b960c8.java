/* @test    @summary Test poll() method of ScheduledThreadPoolExecutor for non-empty queue with ready task    @run main ScheduledThreadPoolExecutorWitupTest */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testPollWithReadyTask();
        testPollWithEmptyQueue();
    }

    static void testPollWithReadyTask() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        AtomicBoolean taskExecuted = new AtomicBoolean(false);
        Runnable task = () -> taskExecuted.set(true);
        RunnableScheduledFuture<?> scheduledFuture = executor.schedule(task, 0, TimeUnit.MILLISECONDS);
        // Wait briefly to ensure task is scheduled
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // ignore
        }
        // Access the private queue via reflection is not allowed; instead, submit a task and poll
        RunnableScheduledFuture<?> polled = executor.poll();
        // Since the task is scheduled with zero delay, poll should return a non-null future
        if (polled != null) {
            // Finish the task to clean up
            executor.shutdownNow();
        }
        assert (polled != null) : "Expected non-null poll result for ready task";
        // Wait for task to execute
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        assert (taskExecuted.get()) : "Task should have been executed";
    }

    static void testPollWithEmptyQueue() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        // Immediately poll; queue should be empty
        RunnableScheduledFuture<?> result = executor.poll();
        assert (result == null) : "Expected null poll result on empty queue";
        executor.shutdownNow();
    }
}
