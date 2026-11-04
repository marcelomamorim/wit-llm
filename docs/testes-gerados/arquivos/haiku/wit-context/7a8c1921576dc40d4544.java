/*
 * @test
 * @summary Verifies that join() on a MinimalStage (obtained via minimalCompletionStage) throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<String> base = new CompletableFuture<>();
        CompletionStage<String> stage = base.minimalCompletionStage();
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;
        try {
            minimal.join();
            throw new AssertionError("expected UnsupportedOperationException from MinimalStage.join()");
        } catch (UnsupportedOperationException expected) {
            // expected behavior
        }
    }
}
