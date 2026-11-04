/*
 * @test
 * @summary Verifies CompletableFuture.completeExceptionally null handling and observable completion behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testNullLeavesFutureIncomplete();
        testCompleteExceptionallyCompletesFuture();
        testSecondCompletionReturnsFalse();
        testGetThrowsExecutionExceptionWithCause();
        testJoinThrowsCompletionExceptionWithCause();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        CompletableFuture<String> f = new CompletableFuture<>();
        try {
            f.completeExceptionally(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullLeavesFutureIncomplete() {
        CompletableFuture<String> f = new CompletableFuture<>();
        try {
            f.completeExceptionally(null);
        } catch (NullPointerException expected) {
            // expected
        }
        check(!f.isDone(), "future must not be done after null completion");
        check(!f.isCompletedExceptionally(), "future must not be completed exceptionally");
    }

    static void testCompleteExceptionallyCompletesFuture() {
        CompletableFuture<String> f = new CompletableFuture<>();
        RuntimeException ex = new RuntimeException("boom");
        boolean triggered = f.completeExceptionally(ex);
        check(triggered, "first completeExceptionally must return true");
        check(f.isDone(), "future must be done");
        check(f.isCompletedExceptionally(), "future must be completed exceptionally");
    }

    static void testSecondCompletionReturnsFalse() {
        CompletableFuture<String> f = new CompletableFuture<>();
        check(f.complete("x"), "complete must return true on first completion");
        boolean triggered = f.completeExceptionally(new RuntimeException("late"));
        check(!triggered, "completeExceptionally after normal completion must return false");
        check(!f.isCompletedExceptionally(), "future must remain normally completed");
        check("x".equals(f.join()), "join must return the original value");
    }

    static void testGetThrowsExecutionExceptionWithCause() throws InterruptedException {
        CompletableFuture<String> f = new CompletableFuture<>();
        RuntimeException ex = new RuntimeException("boom");
        f.completeExceptionally(ex);
        try {
            f.get();
            throw new AssertionError("expected ExecutionException");
        } catch (ExecutionException e) {
            check(e.getCause() == ex, "cause must be the completing exception");
        }
    }

    static void testJoinThrowsCompletionExceptionWithCause() {
        CompletableFuture<String> f = new CompletableFuture<>();
        RuntimeException ex = new RuntimeException("boom");
        f.completeExceptionally(ex);
        try {
            f.join();
            throw new AssertionError("expected CompletionException");
        } catch (CompletionException e) {
            check(e.getCause() == ex, "cause must be the completing exception");
        }
    }
}
