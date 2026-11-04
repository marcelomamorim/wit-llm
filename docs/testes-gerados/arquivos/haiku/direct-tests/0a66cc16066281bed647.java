/*
 * @test
 * @summary Verify CompletableFuture.thenRunAsync runs the action after normal completion, propagates exceptional completion, and does not run the action in that case
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNormalCompletionRunsActionAndYieldsNull();
        testDelayedCompletionRunsActionOnce();
        testExceptionalSourcePropagatesWithoutRunningAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNormalCompletionRunsActionAndYieldsNull() {
        CompletableFuture<String> source = new CompletableFuture<>();
        int[] ran = {0};
        source.complete("value");
        CompletableFuture<Void> result = source.thenRunAsync(() -> ran[0]++);
        Void value = result.join();
        check(value == null, "thenRunAsync result must be null");
        check(ran[0] == 1, "action must run exactly once, ran=" + ran[0]);
        check(result.isDone(), "result must be done");
        check(!result.isCompletedExceptionally(), "result must not be exceptional");
    }

    static void testDelayedCompletionRunsActionOnce() {
        CompletableFuture<String> source = new CompletableFuture<>();
        int[] ran = {0};
        CompletableFuture<Void> result = source.thenRunAsync(() -> ran[0]++);
        check(!result.isDone(), "result must not be done before source completes");
        check(ran[0] == 0, "action must not run before source completes");
        source.complete("later");
        Void value = result.join();
        check(value == null, "delayed thenRunAsync result must be null");
        check(ran[0] == 1, "delayed action must run exactly once, ran=" + ran[0]);
    }

    static void testExceptionalSourcePropagatesWithoutRunningAction() {
        CompletableFuture<String> source = new CompletableFuture<>();
        RuntimeException cause = new RuntimeException("boom");
        source.completeExceptionally(cause);
        int[] ran = {0};
        CompletableFuture<Void> result = source.thenRunAsync(() -> ran[0]++);
        check(result.isCompletedExceptionally(), "result must complete exceptionally");
        try {
            result.join();
            throw new AssertionError("join must throw CompletionException");
        } catch (CompletionException e) {
            check(e.getCause() == cause, "CompletionException cause must be the source exception");
        }
        check(ran[0] == 0, "action must not run when source completed exceptionally, ran=" + ran[0]);
    }
}
