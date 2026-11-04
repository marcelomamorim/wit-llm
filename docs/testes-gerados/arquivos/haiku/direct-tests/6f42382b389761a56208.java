/*
 * @test
 * @summary MinimalStage.isCancelled must throw UnsupportedOperationException, while regular CompletableFuture.isCancelled reports cancellation
 * @run main CompletableFutureWitupTest
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletionStage<String> stage = cf.minimalCompletionStage();
        Future<?> f = (Future<?>) stage;
        try {
            f.isCancelled();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected behavior of MinimalStage.isCancelled()
        }

        CompletableFuture<String> regular = new CompletableFuture<>();
        if (regular.isCancelled()) {
            throw new AssertionError("new future must not be cancelled");
        }
        if (!regular.cancel(true)) {
            throw new AssertionError("cancel should succeed on incomplete future");
        }
        if (!regular.isCancelled()) {
            throw new AssertionError("future should report cancelled");
        }
    }
}
