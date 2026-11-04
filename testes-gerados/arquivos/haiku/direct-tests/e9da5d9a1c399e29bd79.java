/*
 * @test
 * @summary CompletableFuture.get() returns the completed value, and the minimal stage returned by minimalCompletionStage() rejects get() with UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("v");
        if (!"v".equals(cf.get())) {
            throw new AssertionError("get() should return the completed value");
        }

        CompletionStage<String> stage = cf.minimalCompletionStage();
        CompletableFuture<String> minimal = (CompletableFuture<String>) stage;
        try {
            minimal.get();
            throw new AssertionError("expected UnsupportedOperationException from minimal stage get()");
        } catch (UnsupportedOperationException expected) {
            // expected behavior
        }
    }
}
