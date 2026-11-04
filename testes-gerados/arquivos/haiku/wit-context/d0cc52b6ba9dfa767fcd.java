/*
 * @test
 * @summary Witup-generated checks for ThreadPoolExecutor.getLargestPoolSize() observable behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        initialValueIsZero();
        largestReflectsConcurrentWorkers();
        largestRetainedAfterTermination();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void initialValueIsZero() throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2, 2, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        try {
            check(pool.getLargestPoolSize() == 0,
                  "expected 0 before any task, got " + pool.getLargestPoolSize());
        } finally {
            pool.shutdownNow();
            pool.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    static void largestReflectsConcurrentWorkers() throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2, 2, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        final CountDownLatch started = new CountDownLatch(2);
        final CountDownLatch release = new CountDownLatch(1);
        Runnable blocker = new Runnable() {
            public void run() {
                started.countDown();
                try {
                    release.await(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        try {
            pool.execute(blocker);
            pool.execute(blocker);
            check(started.await(30, TimeUnit.SECONDS), "workers did not start");
            check(pool.getLargestPoolSize() == 2,
                  "expected 2 while two workers run, got " + pool.getLargestPoolSize());
        } finally {
            release.countDown();
            pool.shutdown();
            check(pool.awaitTermination(30, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void largestRetainedAfterTermination() throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1, 1, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        final CountDownLatch done = new CountDownLatch(1);
        pool.execute(new Runnable() {
            public void run() {
                done.countDown();
            }
        });
        try {
            check(done.await(30, TimeUnit.SECONDS), "task did not run");
        } finally {
            pool.shutdown();
            check(pool.awaitTermination(30, TimeUnit.SECONDS), "pool did not terminate");
        }
        check(pool.getLargestPoolSize() == 1,
              "expected 1 after termination, got " + pool.getLargestPoolSize());
    }
}
