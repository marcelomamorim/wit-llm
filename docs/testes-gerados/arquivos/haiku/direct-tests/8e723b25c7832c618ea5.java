/*
 * @test
 * @summary Verifies observable behavior of CompletableFuture.failedFuture(Throwable)
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testFailedState();
        testJoinWrapsCause();
        testGetWrapsCause();
        testGetNowWrapsCause();
        testExceptionallyReceivesOriginalException();
        testNullRejected();
        testDistinctInstances();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFailedState() {
        IllegalStateException ex = new IllegalStateException("boom");
        CompletableFuture<String> f = CompletableFuture.failedFuture(ex);
        check(f.isDone(), "future should be done");
        check(f.isCompletedExceptionally(), "future should be completed exceptionally");
        check(!f.isCancelled(), "future should not be cancelled");
    }

    static void testJoinWrapsCause() {
        IllegalStateException ex = new IllegalStateException("boom");
        CompletableFuture<String> f = CompletableFuture.failedFuture(ex);
        boolean thrown = false;
        try {
            f.join();
        } catch (CompletionException e) {
            thrown = true;
            check(e.getCause() == ex, "join cause should be the original exception");
        }
        check(thrown, "join should throw CompletionException");
    }

    static void testGetWrapsCause() throws InterruptedException {
        IllegalStateException ex = new IllegalStateException("boom");
        CompletableFuture<String> f = CompletableFuture.failedFuture(ex);
        boolean thrown = false;
        try {
            f.get();
        } catch (ExecutionException e) {
            thrown = true;
            check(e.getCause() == ex, "get cause should be the original exception");
        }
        check(thrown, "get should throw ExecutionException");
    }

    static void testGetNowWrapsCause() {
        IllegalStateException ex = new IllegalStateException("boom");
        CompletableFuture<String> f = CompletableFuture.failedFuture(ex);
        boolean thrown = false;
        try {
            f.getNow("default");
        } catch (CompletionException e) {
            thrown = true;
            check(e.getCause() == ex, "getNow cause should be the original exception");
        }
        check(thrown, "getNow should throw CompletionException");
    }

    static void testExceptionallyReceivesOriginalException() {
        IllegalStateException ex = new IllegalStateException("boom");
        final Throwable[] received = new Throwable[1];
        String result = CompletableFuture.<String>failedFuture(ex)
                .exceptionally(t -> {
                    received[0] = t;
                    return "recovered";
                })
                .join();
        check("recovered".equals(result), "exceptionally should provide recovery value");
        check(received[0] == ex, "exceptionally should receive the original exception");
    }

    static void testNullRejected() {
        boolean thrown = false;
        try {
            CompletableFuture.failedFuture(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "failedFuture(null) should throw NullPointerException");
    }

    static void testDistinctInstances() {
        IllegalStateException ex = new IllegalStateException("boom");
        CompletableFuture<String> a = CompletableFuture.failedFuture(ex);
        CompletableFuture<String> b = CompletableFuture.failedFuture(ex);
        check(a != b, "each call should return a new future");
    }
}
