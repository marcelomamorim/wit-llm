/*
 * @test
 * @summary Behavioral checks for CompletableFuture.completeExceptionally(Throwable)
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testFirstCompletionWins();
        testGetThrowsExecutionExceptionWithCause();
        testJoinThrowsCompletionExceptionWithCause();
        testExceptionallyReceivesOriginalThrowable();
        testCompleteExceptionallyAfterValueReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.completeExceptionally(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            check(!cf.isDone(), "future must remain incomplete after NPE");
        }
    }

    static void testFirstCompletionWins() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        RuntimeException first = new RuntimeException("first");
        check(cf.completeExceptionally(first), "first completeExceptionally must return true");
        check(!cf.completeExceptionally(new RuntimeException("second")),
              "second completeExceptionally must return false");
        check(cf.isDone(), "future must be done");
        check(cf.isCompletedExceptionally(), "future must be completed exceptionally");
    }

    static void testGetThrowsExecutionExceptionWithCause() throws InterruptedException {
        CompletableFuture<String> cf = new CompletableFuture<>();
        RuntimeException ex = new RuntimeException("boom");
        cf.completeExceptionally(ex);
        try {
            cf.get();
            throw new AssertionError("expected ExecutionException");
        } catch (ExecutionException e) {
            check(e.getCause() == ex, "cause of ExecutionException must be the original throwable");
        }
    }

    static void testJoinThrowsCompletionExceptionWithCause() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        RuntimeException ex = new RuntimeException("boom");
        cf.completeExceptionally(ex);
        try {
            cf.join();
            throw new AssertionError("expected CompletionException");
        } catch (CompletionException e) {
            check(e.getCause() == ex, "cause of CompletionException must be the original throwable");
        }
    }

    static void testExceptionallyReceivesOriginalThrowable() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        RuntimeException ex = new RuntimeException("boom");
        cf.completeExceptionally(ex);
        Throwable[] seen = new Throwable[1];
        CompletableFuture<String> recovered = cf.exceptionally(t -> {
            seen[0] = t;
            return "recovered";
        });
        check(seen[0] == ex, "exceptionally must receive the original throwable");
        check("recovered".equals(recovered.join()), "exceptionally result mismatch");
    }

    static void testCompleteExceptionallyAfterValueReturnsFalse() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        check(cf.complete("value"), "complete must return true");
        check(!cf.completeExceptionally(new RuntimeException("late")),
              "completeExceptionally after value must return false");
        check(!cf.isCompletedExceptionally(), "future must not be completed exceptionally");
        check("value".equals(cf.getNow(null)), "value must be preserved");
    }
}
