/* @test    @summary Test CompletableFuture.delayedExecutor with valid delay and unit    @run main CompletableFutureWitupTest_P5142bbf69d0d */

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest_P5142bbf69d0d {

    public static void main(String[] args) {
        testDelayedExecutorWithValidDelayAndUnit();
        testDelayedExecutorWithNullUnit();
    }

    private static void testDelayedExecutorWithValidDelayAndUnit() {
        long delay = 100L;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        Executor executor = null;
        try {
            executor = java.util.concurrent.CompletableFuture.delayedExecutor(delay, unit);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for valid delay and unit", t);
        }
        if (executor == null) {
            throw new AssertionError("Executor should not be null for valid delay and unit");
        }
    }

    private static void testDelayedExecutorWithNullUnit() {
        long delay = 100L;
        TimeUnit unit = null;
        boolean exceptionThrown = false;
        try {
            java.util.concurrent.CompletableFuture.delayedExecutor(delay, unit);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type when unit is null", t);
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException expected when unit is null");
        }
    }
}
