/* @test    @summary Test completeExceptionally with a non-null Throwable    @run main CompletableFutureWitupTest_P508a78a47dc8 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P508a78a47dc8 {

    public static void main(String[] args) {
        testCompleteExceptionallyWithNull();
        testCompleteExceptionallyWithThrowable();
    }

    static void testCompleteExceptionallyWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
        } catch (NullPointerException e) {
            System.out.println("Passed: completeExceptionally(null) throws NullPointerException");
            return;
        }
        throw new AssertionError("Expected NullPointerException when passing null");
    }

    static void testCompleteExceptionallyWithThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable ex = new RuntimeException("Test exception");
        boolean result = cf.completeExceptionally(ex);
        if (!result) {
            throw new AssertionError("completeExceptionally should return true when completing");
        }
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture should be completed exceptionally");
        }
        try {
            cf.get();
            throw new AssertionError("cf.get() should throw an exception");
        } catch (java.util.concurrent.ExecutionException ee) {
            if (ee.getCause() != ex) {
                throw new AssertionError("Cause of ExecutionException should be the original exception", ee);
            }
            System.out.println("Passed: completeExceptionally with a Throwable");
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new AssertionError(ie);
        }
    }
}
