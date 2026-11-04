/*
 * @test
 * @summary Witup-derived tests for CompletableFuture.completeAsync(Supplier)
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testNullSupplierThrowsNPE() {
        CompletableFuture<String> f = new CompletableFuture<>();
        boolean thrown = false;
        try {
            f.completeAsync(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("expected NullPointerException for null supplier");
        }
        if (f.isDone()) {
            fail("future must not be completed after NPE");
        }
    }

    static void testReturnsSameInstance() {
        CompletableFuture<String> f = new CompletableFuture<>();
        CompletableFuture<String> r = f.completeAsync(() -> "value");
        if (r != f) {
            fail("completeAsync must return this");
        }
    }

    static void testCompletesWithSupplierValue() {
        CompletableFuture<String> f = new CompletableFuture<>();
        f.completeAsync(() -> "value");
        String result = f.join();
        if (!"value".equals(result)) {
            fail("unexpected result: " + result);
        }
    }

    public static void main(String[] args) {
        testNullSupplierThrowsNPE();
        testReturnsSameInstance();
        testCompletesWithSupplierValue();
    }
}
