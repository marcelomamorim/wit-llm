/*
 * @test
 * @summary Verify CompletableFuture.isCompletedExceptionally behaviour on a regular future and on the minimal stage view that throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        if (f.isCompletedExceptionally()) {
            throw new AssertionError("fresh future should not be completed exceptionally");
        }

        CompletableFuture<String> m = (CompletableFuture<String>) f.minimalCompletionStage();
        try {
            m.isCompletedExceptionally();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected behaviour of the minimal stage view
        }
    }
}
