/*
 * @test
 * @summary Verify CompletableFuture.thenAccept rejects a null action and propagates source state to dependents
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testActionReceivesValue();
        testExceptionalSourcePropagates();
    }

    static void testNullActionThrowsNPE() {
        CompletableFuture<String> f = CompletableFuture.completedFuture("v");
        boolean thrown = false;
        try {
            f.thenAccept(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testActionReceivesValue() throws Exception {
        int[] seen = new int[1];
        CompletableFuture<String> f = CompletableFuture.completedFuture("abc");
        CompletableFuture<Void> d = f.thenAccept(s -> seen[0] = s.length());
        Void result = d.join();
        if (result != null) {
            throw new RuntimeException("thenAccept result must be null, got " + result);
        }
        if (seen[0] != 3) {
            throw new RuntimeException("action did not receive value, seen=" + seen[0]);
        }
    }

    static void testExceptionalSourcePropagates() {
        CompletableFuture<String> f = new CompletableFuture<>();
        RuntimeException cause = new RuntimeException("boom");
        f.completeExceptionally(cause);
        boolean[] ran = new boolean[1];
        CompletableFuture<Void> d = f.thenAccept(s -> ran[0] = true);
        if (!d.isCompletedExceptionally()) {
            throw new RuntimeException("dependent should complete exceptionally");
        }
        if (ran[0]) {
            throw new RuntimeException("action must not run when source failed");
        }
        try {
            d.join();
            throw new RuntimeException("join should have thrown CompletionException");
        } catch (CompletionException e) {
            if (e.getCause() != cause) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
