/*
 * @test
 * @summary Checks public observable behavior of CompletableFuture.cancel(boolean)
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testCancelPendingFuture();
        testCancelCompletedFuture();
    }

    static void testCancelPendingFuture() throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        if (!f.cancel(true)) {
            throw new RuntimeException("cancel(true) on pending future should return true");
        }
        if (!f.isCancelled()) {
            throw new RuntimeException("future should be cancelled");
        }
        if (!f.isDone()) {
            throw new RuntimeException("cancelled future should be done");
        }
        try {
            f.get();
            throw new RuntimeException("get() should throw CancellationException");
        } catch (CancellationException expected) {
            // expected
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("unexpected exception", e);
        }
    }

    static void testCancelCompletedFuture() {
        CompletableFuture<String> g = CompletableFuture.completedFuture("x");
        if (g.cancel(false)) {
            throw new RuntimeException("cancel() on completed future should return false");
        }
        if (g.isCancelled()) {
            throw new RuntimeException("completed future should not be cancelled");
        }
        if (!"x".equals(g.join())) {
            throw new RuntimeException("value should be preserved after failed cancel");
        }
    }
}
