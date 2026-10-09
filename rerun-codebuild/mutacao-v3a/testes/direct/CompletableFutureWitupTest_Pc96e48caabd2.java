/* @test    @summary Test CompletableFuture.failedFuture throws NullPointerException with null argument    @run main CompletableFutureWitupTest_Pc96e48caabd2 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_Pc96e48caabd2 {

    public static void main(String[] args) {
        testFailedFutureWithNull();
        testFailedFutureWithException();
    }

    static void testFailedFutureWithNull() {
        try {
            CompletableFuture.failedFuture(null);
            // If no exception, fail
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testFailedFutureWithException() {
        Throwable ex = new RuntimeException("test exception");
        CompletableFuture<Void> cf = CompletableFuture.failedFuture(ex);
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture should be completed exceptionally");
        }
        try {
            cf.get();
            throw new AssertionError("Expected ExecutionException not thrown");
        } catch (java.util.concurrent.ExecutionException e) {
            if (e.getCause() != ex) {
                throw new AssertionError("Cause of ExecutionException does not match", e);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("InterruptedException thrown", e);
        }
    }
}
