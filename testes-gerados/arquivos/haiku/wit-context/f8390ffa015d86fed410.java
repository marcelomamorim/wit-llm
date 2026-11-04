/*
 * @test
 * @summary Verifies that complete() on a minimal CompletionStage view throws UnsupportedOperationException and leaves the future incomplete
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        CompletionStage<String> stage = new CompletableFuture<String>().minimalCompletionStage();
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;

        boolean thrown = false;
        try {
            minimal.complete("value");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("complete() on minimal stage must throw UnsupportedOperationException");
        }
        if (minimal.isDone()) {
            throw new AssertionError("minimal stage must not be completed after failed complete()");
        }
        if (minimal.isCompletedExceptionally()) {
            throw new AssertionError("minimal stage must not be completed exceptionally after failed complete()");
        }
    }
}
