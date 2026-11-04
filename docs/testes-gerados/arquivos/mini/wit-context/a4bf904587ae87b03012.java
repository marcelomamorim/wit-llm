/*
 * @test
 * @summary Test CompletableFuture.runAsync(Runnable) for normal and exceptional behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testRunAsyncWithValidRunnable();
        testRunAsyncWithNullRunnable();
    }

    private static void testRunAsyncWithValidRunnable() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(new Runnable() {
            public void run() {
                // no-op
            }
        });
        if (cf == null) {
            throw new AssertionError("runAsync returned null CompletableFuture");
        }
        // Wait for completion to ensure no exceptions thrown asynchronously
        cf.join();
    }

    private static void testRunAsyncWithNullRunnable() {
        try {
            CompletableFuture.runAsync(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
