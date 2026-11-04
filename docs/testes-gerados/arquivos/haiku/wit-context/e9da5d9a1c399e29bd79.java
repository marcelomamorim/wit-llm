/*
 * @test
 * @summary WITUP-derived check that get() on the minimal completion stage throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("ok");
        if (!"ok".equals(cf.get())) {
            throw new AssertionError("get() on completed future returned unexpected value");
        }

        CompletionStage<String> stage = cf.minimalCompletionStage();
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;
        try {
            minimal.get();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected behavior
        }
    }
}
