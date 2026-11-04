/*
 * @test
 * @summary Verifies that completeExceptionally on the minimal stage view of CompletableFuture throws UnsupportedOperationException and leaves the future incomplete
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        CompletableFuture<Integer> cf = new CompletableFuture<>();
        CompletionStage<Integer> stage = cf.minimalCompletionStage();
        CompletableFuture<Integer> minimal = (CompletableFuture<Integer>) stage;

        boolean thrown = false;
        try {
            minimal.completeExceptionally(new RuntimeException("boom"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException");
        }
        if (minimal.isDone()) {
            throw new AssertionError("minimal stage must not be completed");
        }
        if (cf.isDone()) {
            throw new AssertionError("source future must not be completed");
        }
    }
}
