/*
 * @test
 * @summary Behavioral tests for ScheduledThreadPoolExecutor.shutdown() using only public APIs
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testShutdownDoesNotThrowAndIsIdempotent();
        testShutdownRejectsNewScheduledTasks();
        testDelayedTaskStillRunsAfterShutdown();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testShutdownDoesNotThrowAndIsIdempotent() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        ex.shutdown();
        check(ex.isShutdown(), "isShutdown should be true after shutdown");
        ex.shutdown();
        check(ex.isShutdown(), "isShutdown should remain true after second shutdown");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate");
        check(ex.isTerminated(), "isTerminated should be true");
    }

    static void testShutdownRejectsNewScheduledTasks() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        ex.shutdown();
        try {
            ex.schedule(() -> { }, 10, TimeUnit.MILLISECONDS);
            throw new RuntimeException("expected RejectedExecutionException after shutdown");
        } catch (RejectedExecutionException expected) {
            // expected
        }
    }

    static void testDelayedTaskStillRunsAfterShutdown() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        AtomicInteger counter = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(1);
        ex.schedule(() -> {
            counter.incrementAndGet();
            latch.countDown();
        }, 50, TimeUnit.MILLISECONDS);
        ex.shutdown();
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate after delayed task");
        check(latch.getCount() == 0, "delayed task should have run before termination");
        check(counter.get() == 1, "delayed task should run exactly once");
    }
}
