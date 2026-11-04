/*
 * @test
 * @summary Checks CompletableFuture.delayedExecutor null handling and observable executor behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullUnitThrowsNPE();
        testReturnsNonNullExecutor();
        testDelayedExecutorRunsTask();
    }

    static void testNullUnitThrowsNPE() {
        try {
            CompletableFuture.delayedExecutor(1L, null);
            throw new AssertionError("expected NullPointerException for null unit");
        } catch (NullPointerException expected) {
        }
    }

    static void testReturnsNonNullExecutor() {
        Executor executor = CompletableFuture.delayedExecutor(1L, TimeUnit.MILLISECONDS);
        if (executor == null) {
            throw new AssertionError("delayedExecutor returned null");
        }
    }

    static void testDelayedExecutorRunsTask() throws Exception {
        Executor executor = CompletableFuture.delayedExecutor(10L, TimeUnit.MILLISECONDS);
        AtomicBoolean ran = new AtomicBoolean(false);
        CompletableFuture.runAsync(() -> ran.set(true), executor).get(30, TimeUnit.SECONDS);
        if (!ran.get()) {
            throw new AssertionError("task submitted via delayedExecutor did not run");
        }
    }
}
