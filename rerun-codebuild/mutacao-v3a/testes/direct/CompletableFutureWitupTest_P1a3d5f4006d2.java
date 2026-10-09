/*
 * @test    @summary Test completeAsync method of CompletableFuture
 * @run main CompletableFutureWitupTest_P1a3d5f4006d2
 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest_P1a3d5f4006d2 {

    public static void main(String[] args) {
        testCompleteAsyncReturnsExpected();
        testCompleteAsyncWithNullSupplier();
    }

    private static void testCompleteAsyncReturnsExpected() {
        Supplier<String> supplier = () -> "test_value";
        CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture<String> result = future.completeAsync(supplier);
        String value = result.join();
        assert value.equals("test_value") : "Expected 'test_value', but got: " + value;
    }

    private static void testCompleteAsyncWithNullSupplier() {
        try {
            CompletableFuture<String> future = new CompletableFuture<>();
            future.completeAsync(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
