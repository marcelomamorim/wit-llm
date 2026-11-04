/* @test    @summary Test supplyAsync method of CompletableFuture for JDK 11+28    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testSupplyAsyncReturnsExpectedValue();
        testSupplyAsyncWithNullSupplier();
    }

    public static void testSupplyAsyncReturnsExpectedValue() {
        String expected = "test";
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> expected);
        String result = future.join();
        if (!expected.equals(result)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    public static void testSupplyAsyncWithNullSupplier() {
        try {
            CompletableFuture.supplyAsync((Supplier<String>) null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
