/*
 * @test
 * @summary Basic observable behavior of CompletableFuture.supplyAsync(Supplier)
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNormalValue();
        testGetReturnsValue();
        testNullResult();
        testSupplierExceptionViaJoin();
        testSupplierExceptionViaGet();
    }

    static void testNormalValue() {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> "hello");
        check("hello".equals(f.join()), "join should return supplied value");
        check(f.isDone(), "future should be done after join");
        check(!f.isCompletedExceptionally(), "future should not complete exceptionally");
    }

    static void testGetReturnsValue() throws Exception {
        CompletableFuture<Integer> g = CompletableFuture.supplyAsync(() -> 40 + 2);
        check(g.get().intValue() == 42, "get should return 42");
    }

    static void testNullResult() {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> null);
        check(f.join() == null, "null supplier result should be returned as null");
        check(!f.isCompletedExceptionally(), "null result is not an exception");
    }

    static void testSupplierExceptionViaJoin() {
        CompletableFuture<String> h = CompletableFuture.<String>supplyAsync(() -> {
            throw new IllegalStateException("boom");
        });
        try {
            h.join();
            throw new RuntimeException("join should have thrown CompletionException");
        } catch (CompletionException e) {
            check(e.getCause() instanceof IllegalStateException,
                  "cause of CompletionException should be IllegalStateException");
        }
        check(h.isCompletedExceptionally(), "future should complete exceptionally");
    }

    static void testSupplierExceptionViaGet() throws InterruptedException {
        CompletableFuture<String> h = CompletableFuture.<String>supplyAsync(() -> {
            throw new IllegalStateException("boom");
        });
        try {
            h.get();
            throw new RuntimeException("get should have thrown ExecutionException");
        } catch (ExecutionException e) {
            check(e.getCause() instanceof IllegalStateException,
                  "cause of ExecutionException should be IllegalStateException");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
