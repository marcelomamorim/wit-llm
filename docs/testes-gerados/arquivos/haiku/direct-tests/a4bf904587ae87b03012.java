/*
 * @test
 * @summary Behavioral tests for CompletableFuture.runAsync(Runnable) using only public API
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsNonNullFuture() {
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> { });
        check(f != null, "runAsync must return a non-null future");
        f.join();
    }

    static void testRunnableIsExecuted() {
        AtomicInteger counter = new AtomicInteger(0);
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> counter.incrementAndGet());
        f.join();
        check(counter.get() == 1, "runnable must be executed exactly once, was " + counter.get());
    }

    static void testResultIsNullVoid() {
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> { });
        Void result = f.join();
        check(result == null, "Void result of runAsync must be null");
        check(f.isDone(), "future must be done after join");
        check(!f.isCompletedExceptionally(), "future must not complete exceptionally on normal run");
    }

    static void testExceptionInRunnableCompletesExceptionally() {
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> {
            throw new IllegalStateException("boom");
        });
        boolean thrown = false;
        try {
            f.join();
        } catch (CompletionException e) {
            thrown = true;
            check(e.getCause() instanceof IllegalStateException,
                  "cause must be IllegalStateException, was " + e.getCause());
        }
        check(thrown, "join must throw CompletionException when runnable throws");
        check(f.isCompletedExceptionally(), "future must be completed exceptionally");
    }

    public static void main(String[] args) throws Exception {
        testReturnsNonNullFuture();
        testRunnableIsExecuted();
        testResultIsNullVoid();
        testExceptionInRunnableCompletesExceptionally();
    }
}
