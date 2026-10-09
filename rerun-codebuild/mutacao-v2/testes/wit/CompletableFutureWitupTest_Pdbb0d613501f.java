/*
 * @test
 * @summary Test CompletableFuture.completeExceptionally behavior including null argument
 * @run main CompletableFutureWitupTest_Pdbb0d613501f
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest_Pdbb0d613501f {
    public static void main(String[] args) {
        testCompleteExceptionallyWithNull();
        testCompleteExceptionallyWithValidException();
    }

    private static void testCompleteExceptionallyWithNull() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testCompleteExceptionallyWithValidException() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        RuntimeException ex = new RuntimeException("test");
        boolean firstComplete = cf.completeExceptionally(ex);
        if (!firstComplete) {
            throw new AssertionError("completeExceptionally should return true on first completion");
        }
        boolean secondComplete = cf.completeExceptionally(new RuntimeException("second"));
        if (secondComplete) {
            throw new AssertionError("completeExceptionally should return false if already completed");
        }
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture should be completed exceptionally");
        }
    }
}
