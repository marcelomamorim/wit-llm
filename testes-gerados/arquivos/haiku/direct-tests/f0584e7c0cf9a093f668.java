/*
 * @test
 * @summary Verifies ForkJoinPool.execute(Runnable) rejects null and runs submitted Runnables on the pool
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinPoolWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullTaskThrowsNPE() {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            pool.execute(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            pool.shutdown();
        }
    }

    static void testSingleRunnableExecuted() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        try {
            CountDownLatch latch = new CountDownLatch(1);
            pool.execute(latch::countDown);
            check(latch.await(30, TimeUnit.SECONDS), "runnable was not executed");
        } finally {
            pool.shutdown();
            check(pool.awaitTermination(30, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void testManyRunnablesExecuted() throws InterruptedException {
        final int n = 100;
        ForkJoinPool pool = new ForkJoinPool(4);
        try {
            AtomicInteger counter = new AtomicInteger();
            CountDownLatch latch = new CountDownLatch(n);
            for (int i = 0; i < n; i++) {
                pool.execute(() -> {
                    counter.incrementAndGet();
                    latch.countDown();
                });
            }
            check(latch.await(60, TimeUnit.SECONDS), "not all runnables completed");
            check(counter.get() == n, "expected " + n + " but was " + counter.get());
        } finally {
            pool.shutdown();
            check(pool.awaitTermination(60, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testSingleRunnableExecuted();
        testManyRunnablesExecuted();
    }
}
