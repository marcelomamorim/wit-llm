/* @test    @summary Test thenRunAsync with a simple Runnable execution    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testThenRunAsyncExecutesRunnable();
        testThenRunAsyncReturnsCompletedFuture();
    }

    private static void testThenRunAsyncExecutesRunnable() {
        final boolean[] executed = {false};
        Runnable r = () -> executed[0] = true;
        CompletableFuture<Void> cf = new CompletableFuture<>();
        CompletableFuture<Void> result = cf.thenRunAsync(r);
        cf.complete(null);
        result.join();
        assert executed[0] : "Runnable was not executed in thenRunAsync";
    }

    private static void testThenRunAsyncReturnsCompletedFuture() {
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(null);
        Runnable r = () -> {};
        CompletableFuture<Void> result = cf.thenRunAsync(r);
        assert result.isDone() : "Result future is not completed";
        assert !result.isCompletedExceptionally() : "Result future completed exceptionally";
    }
}
