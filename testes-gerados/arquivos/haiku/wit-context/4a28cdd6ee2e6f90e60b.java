/*
 * @test
 * @summary Verifies CompletableFuture.failedStage null handling and exceptional completion
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testFailedStageIsCompletedExceptionally();
        testFailedStageGetWrapsOriginalException();
        testFailedStageReturnsNonNullStage();
    }

    static void testNullThrowsNullPointerException() {
        try {
            CompletableFuture.failedStage(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("failedStage(null) should throw NullPointerException");
    }

    static void testFailedStageIsCompletedExceptionally() {
        Exception ex = new IllegalStateException("boom");
        CompletionStage<String> stage = CompletableFuture.failedStage(ex);
        CompletableFuture<String> cf = stage.toCompletableFuture();
        if (!cf.isCompletedExceptionally()) {
            throw new AssertionError("failedStage result should be completed exceptionally");
        }
        if (!cf.isDone()) {
            throw new AssertionError("failedStage result should be done");
        }
    }

    static void testFailedStageGetWrapsOriginalException() throws Exception {
        Exception ex = new IllegalArgumentException("bad input");
        CompletableFuture<Integer> cf = CompletableFuture.<Integer>failedStage(ex).toCompletableFuture();
        try {
            cf.get();
        } catch (ExecutionException e) {
            if (e.getCause() != ex) {
                throw new AssertionError("ExecutionException cause should be the original exception");
            }
            return;
        }
        throw new AssertionError("get() should throw ExecutionException");
    }

    static void testFailedStageReturnsNonNullStage() {
        CompletionStage<Object> stage = CompletableFuture.failedStage(new RuntimeException("x"));
        if (stage == null) {
            throw new AssertionError("failedStage should not return null");
        }
    }
}
