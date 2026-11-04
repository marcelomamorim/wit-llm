/*
 * @test
 * @summary Verifies CompletableFuture isDone behavior, including the UnsupportedOperationException of the minimal completion stage
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testIsDoneReflectsCompletion();
        testMinimalStageIsDoneThrowsUnsupportedOperation();
    }

    static void testIsDoneReflectsCompletion() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        if (cf.isDone()) {
            throw new AssertionError("new CompletableFuture must not be done");
        }
        cf.complete("value");
        if (!cf.isDone()) {
            throw new AssertionError("CompletableFuture must be done after complete");
        }
    }

    static void testMinimalStageIsDoneThrowsUnsupportedOperation() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletionStage<String> stage = cf.minimalCompletionStage();
        Future<?> future = (Future<?>) stage;
        boolean thrown = false;
        try {
            future.isDone();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("isDone on minimal completion stage must throw UnsupportedOperationException");
        }
    }
}
