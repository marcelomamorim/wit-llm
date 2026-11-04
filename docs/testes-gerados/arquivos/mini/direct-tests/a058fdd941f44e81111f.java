/*
 * @test
 * @summary Test CompletableFuture.completeAsync(Supplier) method behavior
 * @run main CompletableFutureWitupTest
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureWitupTest {
    public static void main(String[] args) throws Exception {
        testCompleteAsyncReturnsCompletableFutureWithSupplierResult();
        testCompleteAsyncWithNullSupplierThrowsNPE();
    }

    static void testCompleteAsyncReturnsCompletableFutureWithSupplierResult() throws Exception {
        CompletableFuture<String> cf = new CompletableFuture<String>().completeAsync(new Supplier<String>() {
            public String get() {
                return "test";
            }
        });
        // The returned CompletableFuture should complete with the supplier's value
        String result = cf.get();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }
    }

    static void testCompleteAsyncWithNullSupplierThrowsNPE() {
        CompletableFuture<String> cf = new CompletableFuture<String>();
        try {
            cf.completeAsync(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
