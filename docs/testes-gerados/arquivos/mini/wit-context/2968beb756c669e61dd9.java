/*
 * @test
 * @summary Test CompletableFuture.delayedExecutor(long, TimeUnit) for null argument and normal behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testNullTimeUnitThrows();
        testDelayedExecutorReturnsNonNull();
        System.out.println("All tests passed.");
    }

    private static void testNullTimeUnitThrows() {
        try {
            CompletableFuture.delayedExecutor(10L, null);
            throw new AssertionError("Expected NullPointerException for null TimeUnit");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testDelayedExecutorReturnsNonNull() {
        Executor exec = CompletableFuture.delayedExecutor(1L, TimeUnit.MILLISECONDS);
        if (exec == null) {
            throw new AssertionError("delayedExecutor returned null Executor");
        }
    }
}
