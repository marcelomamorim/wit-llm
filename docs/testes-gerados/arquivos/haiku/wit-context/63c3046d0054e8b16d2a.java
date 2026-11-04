/*
 * @test
 * @summary Verifies CompletableFuture.cancel observable behavior and the UnsupportedOperationException of minimal completion stages
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCancelOnPlainFuture();
        testCancelOnMinimalStageThrows();
        System.out.println("PASS");
    }

    static void testCancelOnPlainFuture() {
        CompletableFuture<String> f = new CompletableFuture<>();
        if (!f.cancel(true)) {
            throw new AssertionError("cancel(true) on incomplete future should return true");
        }
        if (!f.isCancelled()) {
            throw new AssertionError("future should be cancelled");
        }
        if (!f.isDone()) {
            throw new AssertionError("cancelled future should be done");
        }
        if (f.cancel(false)) {
            throw new AssertionError("second cancel should return false");
        }
    }

    static void testCancelOnMinimalStageThrows() {
        CompletableFuture<String> base = new CompletableFuture<>();
        CompletionStage<String> stage = base.minimalCompletionStage();
        CompletableFuture<String> minimal = stage.toCompletableFuture();
        boolean thrown = false;
        try {
            minimal.cancel(true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("cancel on minimal stage should throw UnsupportedOperationException");
        }
        if (minimal.isCancelled()) {
            throw new AssertionError("minimal stage should not be cancelled after the exception");
        }
    }
}
