/* @test    @summary Test CompletableFuture.completeAsync with a simple supplier    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testCompleteAsyncWithSupplier();
        testCompleteAsyncWithNullSupplier();
    }

    public static void testCompleteAsyncWithSupplier() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Supplier<String> supplier = () -> "test";
        CompletableFuture<String> result = future.completeAsync(supplier);
        String value = result.join();
        assert value.equals("test") : "Expected 'test', but got: " + value;
    }

    public static void testCompleteAsyncWithNullSupplier() {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            future.completeAsync(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
