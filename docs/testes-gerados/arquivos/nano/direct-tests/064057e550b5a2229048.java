/* @test    @summary Test completeExceptionally with null argument throws NullPointerException    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCompleteExceptionallyWithNull();
        testCompleteExceptionallyWithValidThrowable();
    }

    static void testCompleteExceptionallyWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
            // If no exception thrown, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    static void testCompleteExceptionallyWithValidThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable ex = new RuntimeException("Test exception");
        boolean result = cf.completeExceptionally(ex);
        if (!result) {
            throw new AssertionError("completeExceptionally returned false") ;
        }
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture not marked as completed exceptionally") ;
        }
    }
}
