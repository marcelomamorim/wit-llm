/*
 * @test
 * @summary Test CompletableFuture.thenAccept behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        testThenAcceptCompletesWithValue();
        testThenAcceptDoesNotTriggerBeforeCompletion();
        testThenAcceptWithNullActionThrowsNPE();
    }

    static void testThenAcceptCompletesWithValue() throws InterruptedException, ExecutionException {
        CompletableFuture<String> cf = new CompletableFuture<>();
        final AtomicBoolean called = new AtomicBoolean(false);
        CompletableFuture<Void> cf2 = cf.thenAccept(new Consumer<String>() {
            public void accept(String s) {
                if ("test".equals(s)) {
                    called.set(true);
                }
            }
        });
        cf.complete("test");
        cf2.get(); // wait for completion
        if (!called.get()) {
            throw new AssertionError("thenAccept action was not called with expected value");
        }
    }

    static void testThenAcceptDoesNotTriggerBeforeCompletion() throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<>();
        final AtomicBoolean called = new AtomicBoolean(false);
        CompletableFuture<Void> cf2 = cf.thenAccept(new Consumer<String>() {
            public void accept(String s) {
                called.set(true);
            }
        });
        // do not complete cf yet
        Thread.sleep(100); // short wait
        if (called.get()) {
            throw new AssertionError("thenAccept action called before completion");
        }
        cf.complete("done");
        cf2.get();
        if (!called.get()) {
            throw new AssertionError("thenAccept action was not called after completion");
        }
    }

    static void testThenAcceptWithNullActionThrowsNPE() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            cf.thenAccept(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
