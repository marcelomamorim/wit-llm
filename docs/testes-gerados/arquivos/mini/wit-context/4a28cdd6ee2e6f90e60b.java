/*
 * @test
 * @summary Test CompletableFuture.failedStage(Throwable) for null and non-null input
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithThrowable();
        System.out.println("All tests passed.");
    }

    private static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testFailedStageWithThrowable() {
        Throwable ex = new Throwable("test exception");
        CompletionStage<?> stage = CompletableFuture.failedStage(ex);
        if (stage == null) {
            throw new AssertionError("failedStage returned null CompletionStage");
        }
        // The returned CompletionStage should complete exceptionally with the given Throwable
        // Since MinimalStage is package-private, we test observable behavior via toCompletableFuture
        CompletableFuture<?> cf = stage.toCompletableFuture();
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletionStage is not completed exceptionally");
        }
        try {
            cf.join();
            throw new AssertionError("join() did not throw CompletionException");
        } catch (java.util.concurrent.CompletionException ce) {
            if (ce.getCause() != ex) {
                throw new AssertionError("CompletionException cause is not the original Throwable");
            }
        }
    }
}
