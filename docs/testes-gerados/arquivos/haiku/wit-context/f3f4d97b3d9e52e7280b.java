/*
 * @test
 * @summary Verify that the minimal CompletionStage view rejects timed get with UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletionStage<String> stage = cf.minimalCompletionStage();
        Future<?> future = (Future<?>) stage;
        boolean thrown = false;
        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError('MinimalStage.get(long, TimeUnit) must throw UnsupportedOperationException');
        }
    }
}
