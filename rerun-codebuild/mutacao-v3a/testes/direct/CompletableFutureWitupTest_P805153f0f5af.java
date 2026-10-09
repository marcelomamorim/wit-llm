/* @test    @summary Test CompletableFuture.delayedExecutor with valid delay and null TimeUnit   @run main CompletableFutureWitupTest_P805153f0f5af */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest_P805153f0f5af {

    public static void main(String[] args) {
        testDelayedExecutorWithValidDelay();
        testDelayedExecutorWithNullUnit();
    }

    private static void testDelayedExecutorWithValidDelay() {
        long delay = 100; // milliseconds
        Executor executor = CompletableFuture.delayedExecutor(delay, TimeUnit.MILLISECONDS);
        if (executor == null) {
            throw new AssertionError("Executor should not be null for valid delay and TimeUnit");
        }
        System.out.println("Test passed: delayedExecutor with valid delay returns non-null Executor.");
    }

    private static void testDelayedExecutorWithNullUnit() {
        boolean exceptionThrown = false;
        try {
            CompletableFuture.delayedExecutor(100, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException expected when TimeUnit is null");
        }
        System.out.println("Test passed: delayedExecutor with null TimeUnit throws NullPointerException.");
    }
}
