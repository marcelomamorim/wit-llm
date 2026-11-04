/*
 * @test
 * @summary Test CompletableFuture.thenAccept(Consumer) for null argument and normal usage
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        testThenAcceptWithNull();
        testThenAcceptWithValidConsumer();
    }

    static void testThenAcceptWithNull() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.thenAccept(null);
            throw new AssertionError("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testThenAcceptWithValidConsumer() throws InterruptedException, ExecutionException {
        CompletableFuture<String> cf = new CompletableFuture<>();
        final AtomicBoolean called = new AtomicBoolean(false);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                if ("test".equals(s)) {
                    called.set(true);
                }
            }
        };

        CompletableFuture<Void> result = cf.thenAccept(consumer);
        cf.complete("test");

        // Wait for completion
        result.get();

        if (!called.get()) {
            throw new AssertionError("Consumer was not called with the expected value");
        }
    }
}
