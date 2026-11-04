/*
 * @test
 * @summary Verifies ThreadPoolExecutor.getPoolSize() reflects worker count before, during and after shutdown
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            check(pool.getPoolSize() == 0, "initial pool size must be 0");

            CountDownLatch release = new CountDownLatch(1);
            Runnable blocker = () -> {
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            pool.execute(blocker);
            check(pool.getPoolSize() == 1, "after first task pool size must be 1");

            pool.execute(blocker);
            check(pool.getPoolSize() == 2, "after second task pool size must be 2");

            pool.execute(blocker);
            check(pool.getPoolSize() == 2,
                    "queued task must not increase pool size beyond core size");

            release.countDown();
            pool.shutdown();
            check(pool.awaitTermination(30, TimeUnit.SECONDS), "pool must terminate");
            check(pool.getPoolSize() == 0, "pool size must be 0 after termination");
        } finally {
            pool.shutdownNow();
        }
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
