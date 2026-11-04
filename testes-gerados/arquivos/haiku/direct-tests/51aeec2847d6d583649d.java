/*
 * @test
 * @summary Verifies observable behavior of ThreadPoolExecutor.shutdown()
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testIdleShutdownTerminates();
        testShutdownIsIdempotent();
        testQueuedTasksStillRunAfterShutdown();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void awaitQuietly(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    static void testIdleShutdownTerminates() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue);
        check(!e.isShutdown(), "new executor must not be shut down");
        e.shutdown();
        check(e.isShutdown(), "isShutdown must be true after shutdown");
        check(e.awaitTermination(10, TimeUnit.SECONDS), "idle pool must terminate after shutdown");
        check(e.isTerminated(), "isTerminated must be true after termination");
    }

    static void testShutdownIsIdempotent() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue);
        e.shutdown();
        e.shutdown();
        check(e.isShutdown(), "isShutdown must remain true after repeated shutdown");
        check(e.awaitTermination(10, TimeUnit.SECONDS), "pool must terminate after repeated shutdown");
        check(e.isTerminated(), "isTerminated must be true after repeated shutdown");
    }

    static void testQueuedTasksStillRunAfterShutdown() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue);
        final CountDownLatch started = new CountDownLatch(1);
        final CountDownLatch release = new CountDownLatch(1);
        final AtomicInteger ran = new AtomicInteger();
        e.execute(new Runnable() {
            public void run() {
                started.countDown();
                awaitQuietly(release);
            }
        });
        started.await();
        e.execute(new Runnable() {
            public void run() {
                ran.incrementAndGet();
            }
        });
        e.shutdown();
        check(e.isShutdown(), "isShutdown must be true after shutdown with queued work");
        release.countDown();
        check(e.awaitTermination(10, TimeUnit.SECONDS), "pool must terminate after queued work finishes");
        check(ran.get() == 1, "task queued before shutdown must still run");
    }
}
