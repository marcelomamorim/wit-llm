/*
 * @test
 * @summary Test CompletableFuture.failedStage behavior
 * @run main CompletableFutureWitupTest_Pd6e1ae14c463
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest_Pd6e1ae14c463 {
    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testFailedStageWithException() {
        Throwable ex = new RuntimeException("test exception");
        CompletionStage<?> stage = CompletableFuture.failedStage(ex);
        if (stage == null) {
            throw new Error("failedStage returned null");
        }
        // The returned CompletionStage should complete exceptionally with the given exception
        try {
            stage.toCompletableFuture().join();
            throw new Error("Expected exception not thrown on join");
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause != ex) {
                throw new Error("Exception cause mismatch", e);
            }
        }
    }
}
