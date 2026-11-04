/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.poll() behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ExecutionException;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test poll() returns null when queue is empty
        RunnableScheduledFuture<?> result = executor.poll();
        if (result != null) {
            throw new RuntimeException("Expected poll() to return null on empty queue");
        }

        // Schedule a task with delay 0, so it is ready to run
        ScheduledFuture<?> future = executor.schedule(new Runnable() {
            public void run() {}
        }, 0, TimeUnit.NANOSECONDS);

        // Wait briefly to ensure task is enqueued
        Thread.sleep(10);

        // poll() should return the scheduled task since delay <= 0
        RunnableScheduledFuture<?> polled = executor.poll();
        if (polled == null) {
            throw new RuntimeException("Expected poll() to return a task ready to run");
        }

        // The polled task should be the same as the scheduled one
        if (polled != future) {
            throw new RuntimeException("poll() returned unexpected task instance");
        }

        // Schedule a task with positive delay, poll() should return null
        ScheduledFuture<?> delayedFuture = executor.schedule(new Runnable() {
            public void run() {}
        }, 1000, TimeUnit.MILLISECONDS);

        // Immediately poll, should return null because delay > 0
        RunnableScheduledFuture<?> delayedPoll = executor.poll();
        if (delayedPoll != null) {
            throw new RuntimeException("Expected poll() to return null for delayed task");
        }

        executor.shutdownNow();
    }
}
