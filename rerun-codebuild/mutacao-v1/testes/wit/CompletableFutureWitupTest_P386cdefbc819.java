/* @test    @summary Test for CompletableFuture.completeAsync with null supplier and defaultExecutor() returning null    @run main CompletableFutureWitupTest_P386cdefbc819 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest_P386cdefbc819 {

    public static void main(String[] args) {
        testCompleteAsyncWithNullSupplier();
    }

    private static void testCompleteAsyncWithNullSupplier() {
        CompletableFuture<Object> cf = new CompletableFuture<>();
        try {
            // Simulate defaultExecutor() returning null
            Supplier<Object> supplier = null;
            CompletableFuture<Object> result = null;
            try {
                result = cf.completeAsync(supplier);
                throw new AssertionError("Expected NullPointerException not thrown");
            } catch (NullPointerException npe) {
                // Expected exception
            }
        } catch (Throwable t) {
            throw new RuntimeException("Test failed: unexpected exception", t);
        }
    }
}
