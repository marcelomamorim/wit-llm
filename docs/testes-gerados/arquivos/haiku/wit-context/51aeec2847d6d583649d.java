/*
 * @test
 * @summary Behavioral tests for ThreadPoolExecutor.shutdown()
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testShutdownOnIdlePoolTerminates();
        testShutdownIsIdempotent();
        testQueuedTasksStillRunAfterShutdown();
        testSubmitAfterShutdownIsRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ThreadPoolExecutor newPool() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue);
    }

    static void testShutdownOnIdlePoolTerminates() throws Exception {
        ThreadPoolExecutor pool = newPool();
        pool.shutdown();
        check(pool.isShutdown(), "pool must be shut down");
        check(pool.awaitTermination(10, TimeUnit.SECONDS), "idle pool must terminate");
        check(pool.isTerminated(), "pool must be terminated");
    }

    static void testShutdownIsIdempotent() throws Exception {
        ThreadPoolExecutor pool = newPool();
        pool.shutdown();
        pool.shutdown();
        check(pool.isShutdown(), "pool must remain shut down");
        check(pool.awaitTermination(10, TimeUnit.SECONDS), "pool must terminate after repeated shutdown");
    }

    static void testQueuedTasksStillRunAfterShutdown() throws Exception {
        ThreadPoolExecutor pool = newPool();
        final CountDownLatch gate = new CountDownLatch(1);
        final AtomicInteger counter = new AtomicInteger();
        pool.execute(new Runnable() {
            public void run() {
                try {
                    gate.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        for (int i = 0; i < 3; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    counter.incrementAndGet();
                }
            });
        }
        pool.shutdown();
        check(pool.isShutdown(), "pool must be shut down while tasks are pending");
        gate.countDown();
        check(pool.awaitTermination(10, TimeUnit.SECONDS), "pool must terminate after draining queue");
        check(counter.get() == 3, "previously queued tasks must still run, got " + counter.get());
    }

    static void testSubmitAfterShutdownIsRejected() throws Exception {
        ThreadPoolExecutor pool = newPool();
        pool.shutdown();
        boolean rejected = false;
        try {
            pool.execute(new Runnable() {
                public void run() {
                }
            });
        } catch (RejectedExecutionException e) {
            rejected = true;
        }
        check(rejected, "execute after shutdown must be rejected");
        check(pool.awaitTermination(10, TimeUnit.SECONDS), "pool must terminate");
    }
}
