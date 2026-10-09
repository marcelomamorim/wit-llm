/*
 * @test
 * @summary Test CompletableFuture.completeAsync(Supplier) for null supplier and defaultExecutor
 * @run main CompletableFutureWitupTest_P919d9555bd1b
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class CompletableFutureWitupTest_P919d9555bd1b {
    public static void main(String[] args) {
        testCompleteAsyncWithNullSupplier();
        testCompleteAsyncWithValidSupplier();
    }

    static void testCompleteAsyncWithNullSupplier() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Supplier<String> nullSupplier = null;
        Executor executor = null;
        try {
            // We expect NullPointerException if supplier is null or defaultExecutor() returns null
            cf.completeAsync(nullSupplier);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // expected
        }

        // We cannot test defaultExecutor() == null directly as it's protected and no public setter,
        // so this part of the expath is noted but not tested here.
    }

    static void testCompleteAsyncWithValidSupplier() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                return "test";
            }
        };

        CompletableFuture<String> result = cf.completeAsync(supplier);
        if (result == null) {
            throw new AssertionError("completeAsync returned null");
        }
        // The returned CompletableFuture should be the same instance (per source code behavior)
        if (result != cf) {
            throw new AssertionError("completeAsync did not return the same CompletableFuture instance");
        }
    }
}
