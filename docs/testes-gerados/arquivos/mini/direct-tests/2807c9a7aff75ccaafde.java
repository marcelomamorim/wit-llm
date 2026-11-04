/*
 * @test
 * @summary Test CompletableFuture.supplyAsync(Supplier) basic behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        testSupplyAsyncReturnsCompletedValue();
        testSupplyAsyncWithNullSupplierThrowsNPE();
    }

    static void testSupplyAsyncReturnsCompletedValue() throws Exception {
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                return "testValue";
            }
        };
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(supplier);
        // Wait for completion and check value
        String result = cf.get();
        if (!"testValue".equals(result)) {
            throw new AssertionError("Expected 'testValue' but got: " + result);
        }
    }

    static void testSupplyAsyncWithNullSupplierThrowsNPE() {
        try {
            CompletableFuture.supplyAsync(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
