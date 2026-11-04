/*
 * @test
 * @summary Test CompletableFuture.completeExceptionally behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testCompleteExceptionallyNull();
        testCompleteExceptionallyReturnsTrue();
        testCompleteExceptionallyReturnsFalse();
    }

    static void testCompleteExceptionallyNull() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testCompleteExceptionallyReturnsTrue() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        boolean result = cf.completeExceptionally(new RuntimeException("fail"));
        if (!result) throw new Error("Expected completeExceptionally to return true on first completion");
        if (!cf.isCompletedExceptionally()) throw new Error("Expected CompletableFuture to be completed exceptionally");
    }

    static void testCompleteExceptionallyReturnsFalse() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        cf.completeExceptionally(new RuntimeException("fail"));
        boolean result = cf.completeExceptionally(new RuntimeException("fail again"));
        if (result) throw new Error("Expected completeExceptionally to return false when already completed");
    }
}
