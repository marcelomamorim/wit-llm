/* @test    @summary Test for CompletableFuture.completeAsync with non-null supplier and default executor    @run main CompletableFutureWitupTest_P0913768cb6a7 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest_P0913768cb6a7 {

    public static void main(String[] args) {
        testCompleteAsyncWithValidSupplier();
        testCompleteAsyncWithNullSupplier();
    }

    private static void testCompleteAsyncWithValidSupplier() {
        Supplier<String> supplier = () -> "test";
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            CompletableFuture<String> result = future.completeAsync(supplier);
            String value = result.get();
            if (!"test".equals(value)) {
                throw new AssertionError("Expected 'test', but got: " + value);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testCompleteAsyncWithNullSupplier() {
        Supplier<String> supplier = null;
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            CompletableFuture<String> result = future.completeAsync(supplier);
            String value = result.get();
            throw new AssertionError("Expected NullPointerException, but got value: " + value);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
