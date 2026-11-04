/*
 * @test
 * @summary Checks public behavior of CompletableFuture.minimalCompletionStage() which wraps the MinimalStage target
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        CompletionStage<String> s = f.minimalCompletionStage();
        if (s == null) {
            throw new RuntimeException("minimalCompletionStage returned null");
        }
        f.complete("ok");
        String v = s.toCompletableFuture().get();
        if (!"ok".equals(v)) {
            throw new RuntimeException("unexpected value: " + v);
        }

        CompletableFuture<String> g = new CompletableFuture<>();
        CompletionStage<String> s2 = g.minimalCompletionStage();
        g.completeExceptionally(new IllegalStateException("boom"));
        try {
            s2.toCompletableFuture().get();
            throw new RuntimeException("expected ExecutionException");
        } catch (ExecutionException e) {
            if (!(e.getCause() instanceof IllegalStateException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
