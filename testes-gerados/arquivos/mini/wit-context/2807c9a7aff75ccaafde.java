/*
 * @test
 * @summary Test CompletableFuture.supplyAsync(Supplier) for null argument and normal completion
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest {
    public static void main(String[] args) {
        testSupplyAsyncWithNullSupplier();
        testSupplyAsyncWithValidSupplier();
    }

    private static void testSupplyAsyncWithNullSupplier() {
        try {
            CompletableFuture.supplyAsync(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSupplyAsyncWithValidSupplier() {
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                return "test";
            }
        };
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(supplier);
        // Wait for completion and check result
        String result = cf.join();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }
    }
}
