/*
 * @test
 * @summary Test CompletableFuture.runAsync(Runnable) basic behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        testRunAsyncCompletesNormally();
        testRunAsyncWithNullRunnableThrowsNPE();
    }

    static void testRunAsyncCompletesNormally() throws Exception {
        final boolean[] ran = new boolean[1];
        CompletableFuture<Void> cf = CompletableFuture.runAsync(new Runnable() {
            public void run() {
                ran[0] = true;
            }
        });
        // Wait for completion
        cf.get();
        if (!ran[0]) {
            throw new AssertionError("Runnable did not run");
        }
        if (!cf.isDone()) {
            throw new AssertionError("CompletableFuture not done after get()");
        }
        if (cf.isCompletedExceptionally()) {
            throw new AssertionError("CompletableFuture completed exceptionally unexpectedly");
        }
    }

    static void testRunAsyncWithNullRunnableThrowsNPE() {
        try {
            CompletableFuture.runAsync(null);
            throw new AssertionError("Expected NullPointerException for null runnable");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
