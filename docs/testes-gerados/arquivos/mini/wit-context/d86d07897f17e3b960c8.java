/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.poll() behavior and exception paths
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testPollReturnsNullWhenQueueEmptyOrDelayPositive();
        testPollReturnsRunnableScheduledFutureWhenDelayZeroOrNegative();
        testPollLockUnlockBehavior();
    }

    // Test that poll() returns null if queue is empty or first element delay > 0
    private static void testPollReturnsNullWhenQueueEmptyOrDelayPositive() throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Initially queue is empty, poll() should return null
        RunnableScheduledFuture<?> result = executor.poll();
        if (result != null) {
            throw new AssertionError("poll() should return null when queue is empty");
        }

        // Schedule a task with delay > 0, poll() should return null
        RunnableScheduledFuture<?> scheduled = executor.schedule(
                new NoOpRunnable(), 1000, TimeUnit.MILLISECONDS);

        // Immediately poll, delay > 0, expect null
        result = executor.poll();
        if (result != null) {
            throw new AssertionError("poll() should return null when first task delay > 0");
        }

        executor.shutdownNow();
    }

    // Test that poll() returns the scheduled task if delay <= 0
    private static void testPollReturnsRunnableScheduledFutureWhenDelayZeroOrNegative() throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Schedule a task with zero delay
        RunnableScheduledFuture<?> scheduled = executor.schedule(
                new NoOpRunnable(), 0, TimeUnit.MILLISECONDS);

        // Wait briefly to ensure delay elapsed
        Thread.sleep(10);

        RunnableScheduledFuture<?> result = executor.poll();
        if (result == null) {
            throw new AssertionError("poll() should return the task when delay <= 0");
        }

        executor.shutdownNow();
    }

    // Test that poll() acquires and releases the lock properly (observable via no deadlock)
    private static void testPollLockUnlockBehavior() throws Exception {
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Run poll() in a thread to check it does not deadlock
        Thread t = new Thread(new Runnable() {
            public void run() {
                RunnableScheduledFuture<?> r = executor.poll();
                // No assertion needed, just ensure no deadlock or exception
            }
        });

        t.start();
        t.join(1000);

        if (t.isAlive()) {
            throw new AssertionError("poll() appears to deadlock acquiring lock");
        }

        executor.shutdownNow();
    }

    private static class NoOpRunnable implements Runnable {
        public void run() {
            // no-op
        }
    }
}
