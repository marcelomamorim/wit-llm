/*
 * @test
 * @summary Checks public observable behavior of CompletableFuture.isCompletedExceptionally()
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        CompletableFuture<String> pending = new CompletableFuture<>();
        check(!pending.isCompletedExceptionally());

        pending.complete("v");
        check(!pending.isCompletedExceptionally());

        CompletableFuture<String> failed = new CompletableFuture<>();
        failed.completeExceptionally(new IllegalStateException("boom"));
        check(failed.isCompletedExceptionally());

        check(!failed.complete("late"));
        check(failed.isCompletedExceptionally());

        CompletableFuture<String> cancelled = new CompletableFuture<>();
        cancelled.cancel(true);
        check(cancelled.isCancelled());
        check(cancelled.isCompletedExceptionally());

        CompletableFuture<Integer> dependent = failed.thenApply(x -> 1);
        check(dependent.isCompletedExceptionally());
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("check failed");
        }
    }
}
