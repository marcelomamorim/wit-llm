/*
 * @test
 * @summary Witup-derived checks for CompletableFuture.failedFuture(Throwable)
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testFailedFutureIsCompletedExceptionally();
        testGetThrowsExecutionExceptionWithCause();
        testJoinThrowsCompletionExceptionWithCause();
        testGetNowThrowsCompletionExceptionWithCause();
        testExceptionallyReceivesSameThrowable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        boolean thrown = false;
        try {
            CompletableFuture.failedFuture(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "failedFuture(null) must throw NullPointerException");
    }

    static void testFailedFutureIsCompletedExceptionally() {
        RuntimeException ex = new RuntimeException("boom");
        CompletableFuture<String> f = CompletableFuture.failedFuture(ex);
        check(f.isDone(), "failed future must be done");
        check(f.isCompletedExceptionally(), "failed future must be completed exceptionally");
        check(!f.isCancelled(), "failed future must not be cancelled");
    }

    static void testGetThrowsExecutionExceptionWithCause() throws Exception {
        RuntimeException ex = new RuntimeException("get-cause");
        CompletableFuture<Integer> f = CompletableFuture.failedFuture(ex);
        boolean thrown = false;
        try {
            f.get();
        } catch (ExecutionException e) {
            thrown = true;
            check(e.getCause() == ex, "ExecutionException cause must be the original throwable");
        }
        check(thrown, "get() must throw ExecutionException");
    }

    static void testJoinThrowsCompletionExceptionWithCause() {
        RuntimeException ex = new RuntimeException("join-cause");
        CompletableFuture<Integer> f = CompletableFuture.failedFuture(ex);
        boolean thrown = false;
        try {
            f.join();
        } catch (CompletionException e) {
            thrown = true;
            check(e.getCause() == ex, "CompletionException cause must be the original throwable");
        }
        check(thrown, "join() must throw CompletionException");
    }

    static void testGetNowThrowsCompletionExceptionWithCause() {
        RuntimeException ex = new RuntimeException("getNow-cause");
        CompletableFuture<Integer> f = CompletableFuture.failedFuture(ex);
        boolean thrown = false;
        try {
            f.getNow(Integer.valueOf(0));
        } catch (CompletionException e) {
            thrown = true;
            check(e.getCause() == ex, "getNow CompletionException cause must be the original throwable");
        }
        check(thrown, "getNow() must throw CompletionException");
    }

    static void testExceptionallyReceivesSameThrowable() {
        RuntimeException ex = new RuntimeException("exceptionally");
        CompletableFuture<String> f = CompletableFuture.failedFuture(ex);
        final Throwable[] seen = new Throwable[1];
        CompletableFuture<String> recovered = f.exceptionally(t -> {
            seen[0] = t;
            return "recovered";
        });
        check(seen[0] == ex, "exceptionally must receive the original throwable");
        check("recovered".equals(recovered.join()), "exceptionally result must be propagated");
    }
}
