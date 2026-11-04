/*
 * @test
 * @summary Checks observable getPoolSize() behavior of ThreadPoolExecutor across initial, running and terminated states
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            check(pool.getPoolSize() == 0, "initial pool size should be 0");

            final CountDownLatch started = new CountDownLatch(1);
            final CountDownLatch release = new CountDownLatch(1);
            pool.execute(() -> {
                started.countDown();
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            check(pool.getPoolSize() == 1, "pool size should be 1 after submitting first task");
            started.await();
            check(pool.getPoolSize() == 1, "pool size should remain 1 while task is running");

            release.countDown();
            pool.shutdown();
            check(pool.awaitTermination(10, TimeUnit.SECONDS), "pool should terminate");
            check(pool.getPoolSize() == 0, "pool size should be 0 after termination");
        } finally {
            pool.shutdownNow();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
