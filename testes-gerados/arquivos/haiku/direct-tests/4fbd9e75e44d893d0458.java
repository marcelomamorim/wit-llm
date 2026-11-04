/*
 * @test
 * @summary Verifies ThreadPoolExecutor.getActiveCount() reports workers currently running tasks
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testIdlePoolHasNoActiveThreads();
        testActiveCountTracksRunningTasks();
        testActiveCountAfterTermination();
    }

    static void testIdlePoolHasNoActiveThreads() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 60L, TimeUnit.SECONDS, queue);
        try {
            check(pool.getActiveCount() == 0, "idle pool should report 0 active threads");
        } finally {
            pool.shutdownNow();
        }
    }

    static void testActiveCountTracksRunningTasks() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 60L, TimeUnit.SECONDS, queue);
        try {
            CountDownLatch started = new CountDownLatch(2);
            CountDownLatch release = new CountDownLatch(1);
            Runnable blocking = () -> {
                started.countDown();
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            pool.execute(blocking);
            pool.execute(blocking);
            if (!started.await(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("tasks did not start");
            }
            check(pool.getActiveCount() == 2, "two running tasks should report 2 active threads");
            release.countDown();
            pool.shutdown();
            if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("pool did not terminate");
            }
        } finally {
            pool.shutdownNow();
        }
    }

    static void testActiveCountAfterTermination() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, queue);
        try {
            CountDownLatch started = new CountDownLatch(1);
            CountDownLatch release = new CountDownLatch(1);
            pool.execute(() -> {
                started.countDown();
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            if (!started.await(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("task did not start");
            }
            check(pool.getActiveCount() == 1, "running task should report 1 active thread");
            release.countDown();
            pool.shutdown();
            if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("pool did not terminate");
            }
            check(pool.getActiveCount() == 0, "terminated pool should report 0 active threads");
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
