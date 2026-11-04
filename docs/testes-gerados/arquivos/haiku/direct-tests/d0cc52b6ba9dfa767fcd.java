/*
 * @test
 * @summary Verifies ThreadPoolExecutor.getLargestPoolSize() reports the peak number of worker threads observed
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testInitialLargestPoolSizeIsZero();
        testLargestPoolSizeReflectsPeakWorkers();
        testLargestPoolSizeAfterSequentialTask();
    }

    static void testInitialLargestPoolSizeIsZero() throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            check(pool.getLargestPoolSize() == 0, "expected 0 for new pool");
        } finally {
            pool.shutdown();
            check(pool.awaitTermination(30, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void testLargestPoolSizeReflectsPeakWorkers() throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>());
        final CountDownLatch started = new CountDownLatch(3);
        final CountDownLatch release = new CountDownLatch(1);
        Runnable blocker = () -> {
            started.countDown();
            try {
                release.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        for (int i = 0; i < 3; i++) {
            pool.execute(blocker);
        }
        check(started.await(30, TimeUnit.SECONDS), "tasks did not start");
        check(pool.getLargestPoolSize() == 3,
              "expected largest pool size 3 but was " + pool.getLargestPoolSize());
        release.countDown();
        pool.shutdown();
        check(pool.awaitTermination(30, TimeUnit.SECONDS), "pool did not terminate");
        check(pool.getLargestPoolSize() == 3,
              "largest pool size must persist after termination, was " + pool.getLargestPoolSize());
    }

    static void testLargestPoolSizeAfterSequentialTask() throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        final CountDownLatch done = new CountDownLatch(1);
        pool.execute(done::countDown);
        check(done.await(30, TimeUnit.SECONDS), "task did not run");
        pool.shutdown();
        check(pool.awaitTermination(30, TimeUnit.SECONDS), "pool did not terminate");
        check(pool.getLargestPoolSize() == 1,
              "expected largest pool size 1 but was " + pool.getLargestPoolSize());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
