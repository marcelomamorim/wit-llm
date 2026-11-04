/*
 * @test
 * @summary WITUP-derived checks for CompletableFuture.thenRunAsync(Runnable)
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testActionRunsAfterCompletedSource();
        testActionDeferredUntilSourceCompletes();
    }

    static void testNullActionThrowsNPE() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.thenRunAsync(null);
            throw new AssertionError("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testActionRunsAfterCompletedSource() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        CompletableFuture<String> cf = CompletableFuture.completedFuture("v");
        CompletableFuture<Void> result = cf.thenRunAsync(() -> { counter.incrementAndGet(); });
        Void value = result.get();
        if (value != null) {
            throw new AssertionError("expected null result from thenRunAsync, got " + value);
        }
        if (counter.get() != 1) {
            throw new AssertionError("expected action to run once, ran " + counter.get() + " times");
        }
    }

    static void testActionDeferredUntilSourceCompletes() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture<Void> result = cf.thenRunAsync(() -> { counter.incrementAndGet(); });
        if (counter.get() != 0) {
            throw new AssertionError("action must not run before source completes");
        }
        cf.complete("done");
        result.get();
        if (counter.get() != 1) {
            throw new AssertionError("expected action to run once after completion, ran " + counter.get() + " times");
        }
    }
}
