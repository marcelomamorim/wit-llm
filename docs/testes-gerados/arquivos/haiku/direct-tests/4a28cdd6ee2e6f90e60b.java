/*
 * @test
 * @summary CompletableFuture.failedStage rejects null and returns a stage completed exceptionally with the given throwable
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testFailedStageIsCompletedExceptionally();
        testJoinWrapsOriginalException();
        testGetThrowsExecutionExceptionWithOriginalCause();
        testHandleReceivesSameException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        try {
            CompletableFuture.failedStage(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFailedStageIsCompletedExceptionally() {
        RuntimeException ex = new RuntimeException("boom");
        CompletionStage<String> stage = CompletableFuture.failedStage(ex);
        CompletableFuture<String> future = stage.toCompletableFuture();
        check(future.isDone(), "future should be done");
        check(future.isCompletedExceptionally(), "future should be completed exceptionally");
        check(!future.isCancelled(), "future should not be cancelled");
    }

    static void testJoinWrapsOriginalException() {
        IllegalStateException ex = new IllegalStateException("join");
        CompletableFuture<Object> future = CompletableFuture.failedStage(ex).toCompletableFuture();
        try {
            future.join();
            throw new RuntimeException("expected CompletionException");
        } catch (CompletionException e) {
            check(e.getCause() == ex, "cause of join exception should be the original exception");
        }
    }

    static void testGetThrowsExecutionExceptionWithOriginalCause() throws Exception {
        IllegalArgumentException ex = new IllegalArgumentException("get");
        CompletableFuture<Object> future = CompletableFuture.failedStage(ex).toCompletableFuture();
        try {
            future.get();
            throw new RuntimeException("expected ExecutionException");
        } catch (ExecutionException e) {
            check(e.getCause() == ex, "cause of get exception should be the original exception");
        }
    }

    static void testHandleReceivesSameException() throws Exception {
        RuntimeException ex = new RuntimeException("handle");
        CompletionStage<String> stage = CompletableFuture.failedStage(ex);
        CompletableFuture<Throwable> seen = stage.handle((v, t) -> t).toCompletableFuture();
        check(seen.get() == ex, "handle should receive the same exception instance");
    }
}
