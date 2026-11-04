/*
 * @test
 * @summary CompletableFuture.join() on a minimal completion stage must throw UnsupportedOperationException
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("value");
        if (!"value".equals(cf.join())) {
            throw new AssertionError("join() on a normally completed future must return its value");
        }

        CompletionStage<String> stage = cf.minimalCompletionStage();
        CompletableFuture<String> min = (CompletableFuture<String>) stage;
        boolean thrown = false;
        try {
            min.join();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("join() on minimal stage must throw UnsupportedOperationException");
        }
    }
}
