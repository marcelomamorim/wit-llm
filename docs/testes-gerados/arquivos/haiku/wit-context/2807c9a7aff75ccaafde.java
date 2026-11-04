/*
 * @test
 * @summary Verifies CompletableFuture.supplyAsync(Supplier) rejects a null supplier and completes with the supplier result or exception
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSupplierThrowsNpe();
        testSupplierResultIsReturned();
        testSupplierExceptionCompletesExceptionally();
    }

    static void testNullSupplierThrowsNpe() {
        Supplier<String> nullSupplier = null;
        try {
            CompletableFuture.supplyAsync(nullSupplier);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null supplier");
    }

    static void testSupplierResultIsReturned() throws Exception {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> "ok");
        String result = f.get();
        if (!"ok".equals(result)) {
            throw new AssertionError("unexpected result: " + result);
        }
    }

    static void testSupplierExceptionCompletesExceptionally() throws Exception {
        final IllegalStateException boom = new IllegalStateException("boom");
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {
            throw boom;
        });
        try {
            f.get();
        } catch (ExecutionException e) {
            if (e.getCause() != boom) {
                throw new AssertionError("unexpected cause: " + e.getCause());
            }
            return;
        }
        throw new AssertionError("expected ExecutionException");
    }
}
