/* @test    @summary Test for CompletableFuture.failedFuture(Throwable) method    @run main CompletableFutureWitupTest_P5ce3f5beb1f0 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P5ce3f5beb1f0 {

    public static void main(String[] args) {
        testFailedFutureWithNull();
        testFailedFutureWithException();
    }

    private static void testFailedFutureWithNull() {
        try {
            CompletableFuture.failedFuture(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testFailedFutureWithException() {
        Throwable ex = new RuntimeException("Test exception");
        CompletableFuture<?> future = CompletableFuture.failedFuture(ex);
        if (!future.isCompletedExceptionally()) {
            System.err.println("Future should be completed exceptionally");
            System.exit(1);
        }
        try {
            future.get();
            System.err.println("Expected ExecutionException was not thrown");
            System.exit(1);
        } catch (java.util.concurrent.ExecutionException e) {
            if (e.getCause() != ex) {
                System.err.println("Cause of ExecutionException does not match the original exception");
                System.exit(1);
            }
        } catch (InterruptedException e) {
            System.err.println("Test interrupted");
            System.exit(1);
        }
    }
}
