/*
 * @test
 * @summary WITUP-derived checks for CompletableFuture.isCancelled and MinimalStage.isCancelled
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        if (cf.isCancelled()) {
            throw new AssertionError("fresh future reported as cancelled");
        }
        cf.cancel(true);
        if (!cf.isCancelled()) {
            throw new AssertionError("cancelled future not reported as cancelled");
        }

        CompletableFuture<String> base = new CompletableFuture<>();
        CompletionStage<String> stage = base.minimalCompletionStage();
        if (stage instanceof CompletableFuture) {
            CompletableFuture<?> minimal = (CompletableFuture<?>) stage;
            try {
                minimal.isCancelled();
                throw new AssertionError("MinimalStage.isCancelled did not throw UnsupportedOperationException");
            } catch (UnsupportedOperationException expected) {
                // expected
            }
        }
    }
}
