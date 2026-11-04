/*
 * @test
 * @summary Witup-derived tests for CompletableFuture.orTimeout behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullUnitThrowsNPE();
        testReturnsSameInstance();
        testCompletedFutureNotAltered();
        testTimeoutCompletesExceptionally();
    }

    static void testNullUnitThrowsNPE() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.orTimeout(1, null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        if (cf.isDone()) {
            throw new RuntimeException("Future must not be completed after NPE");
        }
    }

    static void testReturnsSameInstance() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture<String> ret = cf.orTimeout(1, TimeUnit.HOURS);
        if (ret != cf) {
            throw new RuntimeException("orTimeout must return this");
        }
        cf.complete("done");
    }

    static void testCompletedFutureNotAltered() throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("x");
        cf.orTimeout(10, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        if (cf.isCompletedExceptionally()) {
            throw new RuntimeException("Completed future must not be completed exceptionally");
        }
        String v = cf.join();
        if (!"x".equals(v)) {
            throw new RuntimeException("Unexpected value: " + v);
        }
    }

    static void testTimeoutCompletesExceptionally() {
        CompletableFuture<String> cf =
            new CompletableFuture<String>().orTimeout(10, TimeUnit.MILLISECONDS);
        try {
            cf.join();
            throw new RuntimeException("Expected CompletionException");
        } catch (CompletionException e) {
            if (!(e.getCause() instanceof java.util.concurrent.TimeoutException)) {
                throw new RuntimeException("Unexpected cause: " + e.getCause(), e);
            }
        }
        if (!cf.isCompletedExceptionally()) {
            throw new RuntimeException("Future should be completed exceptionally on timeout");
        }
    }
}
