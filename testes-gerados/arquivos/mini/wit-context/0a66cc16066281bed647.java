/*
 * @test
 * @summary Test CompletableFuture.thenRunAsync(Runnable) for normal and exceptional cases
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testThenRunAsyncWithValidRunnable();
        testThenRunAsyncWithNullRunnable();
    }

    private static void testThenRunAsyncWithValidRunnable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Runnable action = new Runnable() {
            boolean ran = false;
            public void run() {
                ran = true;
            }
        };
        // Use a Runnable that sets a flag when run
        final boolean[] executed = new boolean[1];
        Runnable runnable = new Runnable() {
            public void run() {
                executed[0] = true;
            }
        };

        CompletableFuture<Void> result = cf.thenRunAsync(runnable);

        // Complete the original CompletableFuture to trigger the action
        cf.complete(null);

        // Wait for completion
        result.join();

        if (!executed[0]) {
            throw new AssertionError("Runnable action was not executed asynchronously after completion");
        }
    }

    private static void testThenRunAsyncWithNullRunnable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.thenRunAsync(null);
            throw new AssertionError("Expected NullPointerException when passing null Runnable");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
