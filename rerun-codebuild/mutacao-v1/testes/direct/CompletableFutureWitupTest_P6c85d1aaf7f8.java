/* @test    @summary Test CompletableFuture.orTimeout method for timeout behavior    @run main CompletableFutureWitupTest_P6c85d1aaf7f8 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest_P6c85d1aaf7f8 {

    public static void main(String[] args) {
        testOrTimeoutWithNullUnit();
        testOrTimeoutNormalExecution();
        testOrTimeoutTimeout();
    }

    private static void testOrTimeoutWithNullUnit() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testOrTimeoutNormalExecution() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture<String> result = cf.orTimeout(1, TimeUnit.SECONDS);
        if (result != cf) {
            throw new AssertionError("orTimeout should return the same CompletableFuture instance");
        }
        // Complete the future before timeout
        cf.complete("done");
        if (!"done".equals(cf.getNow(null))) {
            throw new AssertionError("CompletableFuture did not complete with expected value");
        }
    }

    private static void testOrTimeoutTimeout() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        CompletableFuture<Void> result = cf.orTimeout(10, TimeUnit.MILLISECONDS);
        try {
            result.get(1, TimeUnit.SECONDS);
            throw new AssertionError("Expected TimeoutException not thrown");
        } catch (java.util.concurrent.TimeoutException e) {
            // Expected timeout
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
