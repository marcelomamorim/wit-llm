/*
 * @test
 * @summary Test CompletableFuture.orTimeout behavior including null TimeUnit and scheduling
 * @run main CompletableFutureWitupTest_P88820d75939f
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class CompletableFutureWitupTest_P88820d75939f {
    public static void main(String[] args) throws Exception {
        testOrTimeoutNullUnit();
        testOrTimeoutSchedulesTimeout();
    }

    static void testOrTimeoutNullUnit() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1, null);
            throw new AssertionError("Expected NullPointerException for null TimeUnit");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOrTimeoutSchedulesTimeout() throws Exception {
        final CompletableFuture<String> cf = new CompletableFuture<>();
        cf.orTimeout(100, TimeUnit.MILLISECONDS);
        // The future should complete exceptionally with TimeoutException after ~100ms
        try {
            cf.get(200, TimeUnit.MILLISECONDS);
            throw new AssertionError("Expected TimeoutException due to orTimeout");
        } catch (ExecutionException ee) {
            Throwable cause = ee.getCause();
            if (!(cause instanceof TimeoutException)) {
                throw new AssertionError("Expected cause to be TimeoutException, got " + cause);
            }
        }
    }
}
