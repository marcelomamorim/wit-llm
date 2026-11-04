/*
 * @test
 * @summary Test CompletableFuture.thenRunAsync(Runnable) basic behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        testThenRunAsyncExecutesAction();
        testThenRunAsyncWithNullActionThrowsNPE();
    }

    static void testThenRunAsyncExecutesAction() throws Exception {
        final boolean[] ran = new boolean[1];
        CompletableFuture<Void> cf = new CompletableFuture<>();
        CompletableFuture<Void> result = cf.thenRunAsync(new Runnable() {
            public void run() {
                ran[0] = true;
            }
        });

        // Complete the original CompletableFuture to trigger the action
        cf.complete(null);

        // Wait for the async action to complete
        result.get();

        if (!ran[0]) {
            throw new AssertionError("Runnable action was not executed");
        }
    }

    static void testThenRunAsyncWithNullActionThrowsNPE() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.thenRunAsync(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
