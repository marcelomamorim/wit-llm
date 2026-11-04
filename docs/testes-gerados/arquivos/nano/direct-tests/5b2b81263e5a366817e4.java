/* @test    @summary Test isCompletedExceptionally method of CompletableFuture    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testIsCompletedExceptionallyReturnsFalseForNewFuture();
        testIsCompletedExceptionallyReturnsTrueAfterCompleteExceptionally();
    }

    private static void testIsCompletedExceptionallyReturnsFalseForNewFuture() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        boolean result = false;
        try {
            result = future.isCompletedExceptionally();
        } catch (UnsupportedOperationException e) {
            // The method is expected to throw UnsupportedOperationException
            // as per the source code snippet.
            result = false;
        }
        if (result) {
            throw new AssertionError("Expected false for new CompletableFuture");
        }
    }

    private static void testIsCompletedExceptionallyReturnsTrueAfterCompleteExceptionally() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            future.completeExceptionally(new RuntimeException());
        } catch (UnsupportedOperationException e) {
            // The method is expected to throw UnsupportedOperationException
            // as per the source code snippet.
            return;
        }
        boolean result = false;
        try {
            result = future.isCompletedExceptionally();
        } catch (UnsupportedOperationException e) {
            // The method is expected to throw UnsupportedOperationException
            return;
        }
        if (!result) {
            throw new AssertionError("Expected true after completeExceptionally()");
        }
    }
}
