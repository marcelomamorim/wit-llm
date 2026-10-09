/* @test    @summary Test for CompletableFuture.delayedExecutor with null TimeUnit to verify NullPointerException is thrown    @run main CompletableFutureWitupTest_P5142bbf69d0d */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest_P5142bbf69d0d {

    public static void main(String[] args) {
        testDelayedExecutorNullUnit();
    }

    public static void testDelayedExecutorNullUnit() {
        try {
            CompletableFuture.delayedExecutor(100, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Test passes, exception was thrown as expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
