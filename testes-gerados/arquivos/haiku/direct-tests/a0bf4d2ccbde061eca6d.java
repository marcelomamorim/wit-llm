/*
 * @test
 * @summary CompletableFuture minimal stage must reject obtrudeException with UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletionStage<String> stage = cf.minimalCompletionStage();
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;

        if (minimal.isDone()) {
            throw new AssertionError("minimal stage should not be done initially");
        }

        boolean thrown = false;
        try {
            minimal.obtrudeException(new RuntimeException("x"));
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException for non-null exception");
        }

        thrown = false;
        try {
            minimal.obtrudeException(null);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException for null exception");
        }

        if (minimal.isDone()) {
            throw new AssertionError("failed obtrude must not complete the minimal stage");
        }
    }
}
