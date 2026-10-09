/* @test    @summary Test completeExceptionally with null argument, expecting NullPointerException    @run main CompletableFutureWitupTest_P195e3b0bcb7a */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_P195e3b0bcb7a {

    public static void main(String[] args) {
        testCompleteExceptionallyWithNull();
        testCompleteExceptionallyWithValidThrowable();
    }

    static void testCompleteExceptionallyWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        boolean exceptionThrown = false;
        try {
            cf.completeExceptionally(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to completeExceptionally");
        }
        if (cf.isDone()) {
            throw new AssertionError("CompletableFuture should not be completed after null argument");
        }
    }

    static void testCompleteExceptionallyWithValidThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable t = new RuntimeException("Test exception");
        boolean result = cf.completeExceptionally(t);
        if (!result) {
            throw new AssertionError("completeExceptionally should return true when completing a pending future");
        }
        if (!cf.isDone()) {
            throw new AssertionError("CompletableFuture should be done after completeExceptionally");
        }
        if (cf.isCompletedExceptionally() != true) {
            throw new AssertionError("CompletableFuture should be marked as completed exceptionally");
        }
        try {
            cf.get();
            throw new AssertionError("cf.get() should throw an ExecutionException");
        } catch (java.util.concurrent.ExecutionException e) {
            if (e.getCause() != t) {
                throw new AssertionError("Cause of ExecutionException should be the original throwable");
            }
        } catch (InterruptedException e) {
            throw new AssertionError("Test interrupted unexpectedly");
        }
    }
}
