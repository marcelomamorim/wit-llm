/*
 * @test
 * @summary Test for CompletableFuture.completeExceptionally(Throwable)
 * @run main CompletableFutureWitupTest_P508a78a47dc8
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P508a78a47dc8 {

    public static void main(String[] args) {
        testCompleteExceptionallyWithNull();
        testCompleteExceptionallyWithValidThrowable();
    }

    public static void testCompleteExceptionallyWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
            System.err.println("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testCompleteExceptionallyWithValidThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable t = new NullPointerException("test exception");
        boolean result = cf.completeExceptionally(t);
        if (!result) {
            System.err.println("Failed to completeExceptionally with valid throwable");
        }
        if (!cf.isCompletedExceptionally()) {
            System.err.println("CompletableFuture is not marked as completed exceptionally");
        }
        try {
            cf.get();
            System.err.println("Expected ExecutionException was not thrown");
        } catch (java.util.concurrent.ExecutionException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                System.err.println("Cause is not NullPointerException");
            }
        } catch (InterruptedException e) {
            System.err.println("Test interrupted");
        }
    }
}
