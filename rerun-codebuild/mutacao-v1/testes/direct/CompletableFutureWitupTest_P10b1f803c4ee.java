/* @test    @summary Test CompletableFuture.failedStage with null and non-null Throwable    @run main CompletableFutureWitupTest_P10b1f803c4ee */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest_P10b1f803c4ee {

    public static void main(String[] args) {
        testFailedStageWithNull();
        testFailedStageWithException();
    }

    static void testFailedStageWithNull() {
        try {
            CompletableFuture.failedStage(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }

    static void testFailedStageWithException() {
        Throwable ex = new RuntimeException("Test exception");
        CompletionStage<?> stage = CompletableFuture.failedStage(ex);
        if (stage == null) {
            throw new AssertionError("failedStage returned null");
        }
        if (!(stage instanceof CompletableFuture)) {
            throw new AssertionError("Returned stage is not a CompletableFuture");
        }
        CompletableFuture<?> cf = (CompletableFuture<?>) stage;
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture should be completed exceptionally");
        }
        try {
            cf.get();
            throw new AssertionError("Expected get() to throw an exception");
        } catch (java.util.concurrent.ExecutionException e) {
            if (e.getCause() != ex) {
                throw new AssertionError("Cause of ExecutionException does not match original exception", e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception from get()", t);
        }
    }
}
