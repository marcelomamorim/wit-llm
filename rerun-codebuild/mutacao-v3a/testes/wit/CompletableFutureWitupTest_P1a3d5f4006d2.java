/* @test    @summary Test completion of completeAsync with non-null supplier and default executor    @run main CompletableFutureWitupTest_P1a3d5f4006d2 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest_P1a3d5f4006d2 {

    public static void main(String[] args) {
        testCompleteAsyncWithValidSupplier();
        testCompleteAsyncWithNullSupplier();
    }

    private static void testCompleteAsyncWithValidSupplier() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Supplier<String> supplier = () -> "result";
        CompletableFuture<String> resultFuture = null;
        try {
            resultFuture = future.completeAsync(supplier);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid supplier", e);
        }
        if (resultFuture == null) {
            throw new AssertionError("completeAsync returned null with valid supplier");
        }
        String result = resultFuture.join();
        if (!"result".equals(result)) {
            throw new AssertionError("Expected 'result', got: " + result);
        }
    }

    private static void testCompleteAsyncWithNullSupplier() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Supplier<String> supplier = null;
        try {
            future.completeAsync(supplier);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
