/*
 * @test
 * @summary CompletableFuture.minimalCompletionStage() returns a stage whose get(timeout, unit) throws UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> source = new CompletableFuture<>();
        source.complete("done");

        String full = source.get(1, TimeUnit.SECONDS);
        if (!"done".equals(full)) {
            throw new AssertionError("Expected get on full CompletableFuture to return 'done' but got: " + full);
        }

        CompletionStage<String> stage = source.minimalCompletionStage();
        CompletableFuture<String> minimal = stage.toCompletableFuture();

        boolean thrown = false;
        try {
            minimal.get(1, TimeUnit.SECONDS);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from get(long, TimeUnit) on minimal stage");
        }
    }
}
