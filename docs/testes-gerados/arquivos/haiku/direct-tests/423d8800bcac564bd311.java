/*
 * @test
 * @summary CompletableFuture.getNow returns the completed value or the fallback; its minimal stage view throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> incomplete = new CompletableFuture<>();
        Object r1 = incomplete.getNow("fallback");
        if (!"fallback".equals(r1)) {
            throw new RuntimeException("Expected fallback for incomplete future, got: " + r1);
        }

        CompletableFuture<String> completed = new CompletableFuture<>();
        completed.complete("value");
        Object r2 = completed.getNow("fallback");
        if (!"value".equals(r2)) {
            throw new RuntimeException("Expected completed value, got: " + r2);
        }

        CompletionStage<String> stage = new CompletableFuture<String>().minimalCompletionStage();
        @SuppressWarnings("unchecked")
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;
        boolean thrown = false;
        try {
            minimal.getNow("fallback");
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from minimal stage getNow");
        }
    }
}
