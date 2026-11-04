/*
 * @test
 * @summary Tests CompletableFuture.delayedExecutor null check and delayed execution
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullUnitThrowsNPE() {
        try {
            CompletableFuture.delayedExecutor(10L, null);
            throw new RuntimeException("Expected NullPointerException for null unit");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsNonNullExecutor() {
        Executor e = CompletableFuture.delayedExecutor(0L, TimeUnit.MILLISECONDS);
        check(e != null, "delayedExecutor must return a non-null Executor");
    }

    static void testTaskRunsAfterDelay() throws InterruptedException {
        final long delayMillis = 50L;
        Executor e = CompletableFuture.delayedExecutor(delayMillis, TimeUnit.MILLISECONDS);
        CountDownLatch latch = new CountDownLatch(1);
        final long start = System.nanoTime();
        e.execute(latch::countDown);
        boolean ran = latch.await(10, TimeUnit.SECONDS);
        long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        check(ran, "Delayed task was not executed");
        check(elapsedMillis >= delayMillis - 10,
              "Task ran too early: elapsed=" + elapsedMillis + "ms");
    }

    static void testZeroDelayTaskRuns() throws InterruptedException {
        Executor e = CompletableFuture.delayedExecutor(0L, TimeUnit.NANOSECONDS);
        CountDownLatch latch = new CountDownLatch(1);
        e.execute(latch::countDown);
        check(latch.await(10, TimeUnit.SECONDS), "Zero-delay task was not executed");
    }

    public static void main(String[] args) throws Exception {
        testNullUnitThrowsNPE();
        testReturnsNonNullExecutor();
        testTaskRunsAfterDelay();
        testZeroDelayTaskRuns();
    }
}
