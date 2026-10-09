/* @test    @summary Test completeAsync with a simple supplier    @run main CompletableFutureWitupTest_P386cdefbc819 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureWitupTest_P386cdefbc819 {

    public static void main(String[] args) {
        testCompleteAsyncWithValue();
        testCompleteAsyncWithNull();
        testCompleteAsyncWithException();
    }

    private static void testCompleteAsyncWithValue() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Supplier<String> supplier = () -> "testValue";
        CompletableFuture<String> result = cf.completeAsync(supplier);
        String value = result.join();
        if (!"testValue".equals(value)) {
            throw new AssertionError("Expected 'testValue', but got: " + value);
        }
    }

    private static void testCompleteAsyncWithNull() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Supplier<String> supplier = () -> null;
        CompletableFuture<String> result = cf.completeAsync(supplier);
        String value = result.join();
        if (value != null) {
            throw new AssertionError("Expected null, but got: " + value);
        }
    }

    private static void testCompleteAsyncWithException() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Supplier<String> supplier = () -> { throw new RuntimeException("fail"); };
        CompletableFuture<String> result = cf.completeAsync(supplier);
        try {
            result.join();
            throw new AssertionError("Expected exception, but none was thrown");
        } catch (Exception e) {
            if (!(e.getCause() instanceof RuntimeException) || !"fail".equals(e.getCause().getMessage())) {
                throw new AssertionError("Unexpected exception: " + e, e);
            }
        }
    }
}
