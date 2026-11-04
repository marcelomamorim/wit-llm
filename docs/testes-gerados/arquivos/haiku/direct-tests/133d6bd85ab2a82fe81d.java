/*
 * @test
 * @summary Verifies the configuration and basic concurrency behavior of Executors.newFixedThreadPool
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testPoolConfiguration(1);
        testPoolConfiguration(4);
        testConcurrentTasksRunInParallel();
        testRejectsNonPositiveThreadCount(0);
        testRejectsNonPositiveThreadCount(-1);
    }

    static void testPoolConfiguration(int nThreads) {
        ExecutorService es = Executors.newFixedThreadPool(nThreads);
        try {
            check(es instanceof ThreadPoolExecutor,
                  "expected a ThreadPoolExecutor");
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
            check(tpe.getCorePoolSize() == nThreads,
                  "corePoolSize should be " + nThreads);
            check(tpe.getMaximumPoolSize() == nThreads,
                  "maximumPoolSize should be " + nThreads);
            check(tpe.getKeepAliveTime(TimeUnit.MILLISECONDS) == 0L,
                  "keep-alive time should be 0 ms");
            check(tpe.getQueue() instanceof LinkedBlockingQueue,
                  "work queue should be a LinkedBlockingQueue");
            check(!es.isShutdown(), "new pool should not be shut down");
        } finally {
            es.shutdownNow();
        }
    }

    static void testConcurrentTasksRunInParallel() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            final CountDownLatch started = new CountDownLatch(2);
            Runnable task = () -> {
                started.countDown();
                try {
                    if (!started.await(10, TimeUnit.SECONDS)) {
                        throw new IllegalStateException("tasks did not run concurrently");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            Future<?> f1 = es.submit(task);
            Future<?> f2 = es.submit(task);
            f1.get(20, TimeUnit.SECONDS);
            f2.get(20, TimeUnit.SECONDS);
            check(started.getCount() == 0, "both tasks should have started");
        } finally {
            es.shutdownNow();
        }
    }

    static void testRejectsNonPositiveThreadCount(int nThreads) {
        try {
            Executors.newFixedThreadPool(nThreads);
            throw new RuntimeException("expected IllegalArgumentException for nThreads=" + nThreads);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
