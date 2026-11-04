/*
 * @test
 * @summary Verifies CompletableFuture.runAsync(Runnable) rejects a null task and runs tasks asynchronously
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRunnableThrowsNPE();
        testRunnableExecutes();
        testFailingRunnableCompletesExceptionally();
    }

    static void testNullRunnableThrowsNPE() {
        try {
            CompletableFuture.runAsync(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null runnable");
    }

    static void testRunnableExecutes() {
        AtomicBoolean ran = new AtomicBoolean();
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> ran.set(true));
        if (f == null) {
            throw new AssertionError("runAsync returned null");
        }
        Void result = f.join();
        if (result != null) {
            throw new AssertionError("expected null result for Void future but was " + result);
        }
        if (!ran.get()) {
            throw new AssertionError("runnable was not executed");
        }
    }

    static void testFailingRunnableCompletesExceptionally() {
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> {
            throw new IllegalStateException("boom");
        });
        try {
            f.join();
        } catch (CompletionException e) {
            if (!(e.getCause() instanceof IllegalStateException)) {
                throw new AssertionError("unexpected cause: " + e.getCause());
            }
            return;
        }
        throw new AssertionError("expected CompletionException");
    }
}
