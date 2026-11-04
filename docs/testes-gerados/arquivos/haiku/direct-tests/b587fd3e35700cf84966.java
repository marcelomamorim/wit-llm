/*
 * @test
 * @summary Witup-generated behavioral tests for CompletableFuture.thenAccept
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testAcceptOnCompletedSource();
        testAcceptDeferredUntilComplete();
        testExceptionalSourcePropagatesAndSkipsAction();
        testActionThrowingCompletesExceptionally();
        testReturnsNewFutureInstance();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testAcceptOnCompletedSource() throws Exception {
        CompletableFuture<Integer> f = new CompletableFuture<>();
        f.complete(5);
        AtomicReference<Integer> seen = new AtomicReference<>();
        CompletableFuture<Void> r = f.thenAccept(seen::set);
        check(seen.get() != null && seen.get() == 5, "action should receive 5");
        check(r.isDone(), "result should be done");
        check(!r.isCompletedExceptionally(), "result should not be exceptional");
        check(r.get() == null, "result value should be null");
    }

    static void testAcceptDeferredUntilComplete() throws Exception {
        CompletableFuture<Integer> f = new CompletableFuture<>();
        AtomicReference<String> seen = new AtomicReference<>();
        CompletableFuture<Void> r = f.thenAccept(v -> seen.set("v" + v));
        check(seen.get() == null, "action must not run before completion");
        check(!r.isDone(), "result must not be done before completion");
        f.complete(7);
        check("v7".equals(seen.get()), "action should run with 7");
        check(r.isDone(), "result should be done after completion");
        check(r.get() == null, "result value should be null");
    }

    static void testExceptionalSourcePropagatesAndSkipsAction() throws Exception {
        CompletableFuture<Integer> f = new CompletableFuture<>();
        AtomicInteger calls = new AtomicInteger();
        f.completeExceptionally(new IllegalStateException("boom"));
        CompletableFuture<Void> r = f.thenAccept(v -> calls.incrementAndGet());
        check(calls.get() == 0, "action must not run for exceptional source");
        check(r.isCompletedExceptionally(), "result should be completed exceptionally");
        try {
            r.get();
            throw new AssertionError("expected ExecutionException");
        } catch (ExecutionException e) {
            check(e.getCause() instanceof IllegalStateException,
                  "cause should be IllegalStateException");
            check("boom".equals(e.getCause().getMessage()), "cause message should be boom");
        }
    }

    static void testActionThrowingCompletesExceptionally() throws Exception {
        CompletableFuture<Integer> f = new CompletableFuture<>();
        f.complete(1);
        CompletableFuture<Void> r = f.thenAccept(v -> {
            throw new IllegalArgumentException("bad");
        });
        check(r.isCompletedExceptionally(), "result should be exceptional when action throws");
        try {
            r.join();
            throw new AssertionError("expected CompletionException");
        } catch (CompletionException e) {
            check(e.getCause() instanceof IllegalArgumentException,
                  "cause should be IllegalArgumentException");
        }
    }

    static void testReturnsNewFutureInstance() throws Exception {
        CompletableFuture<Integer> f = new CompletableFuture<>();
        f.complete(1);
        CompletableFuture<Void> r = f.thenAccept(v -> { });
        check(r != f, "thenAccept should return a new future");
        check(r.get() == null, "result value should be null");
    }
}
