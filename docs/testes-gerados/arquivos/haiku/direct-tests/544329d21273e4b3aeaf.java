/*
 * @test
 * @summary Verify CompletableFuture.isDone() and that the minimal CompletionStage view rejects isDone() with UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        if (cf.isDone()) {
            throw new AssertionError("new CompletableFuture should not be done");
        }
        cf.complete("v");
        if (!cf.isDone()) {
            throw new AssertionError("completed CompletableFuture should be done");
        }

        CompletionStage<String> stage = new CompletableFuture<String>().minimalCompletionStage();
        if (!(stage instanceof Future)) {
            throw new AssertionError("minimal stage should implement Future");
        }
        Future<?> future = (Future<?>) stage;
        boolean thrown = false;
        try {
            future.isDone();
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("isDone() on minimal stage should throw UnsupportedOperationException");
        }
    }
}
