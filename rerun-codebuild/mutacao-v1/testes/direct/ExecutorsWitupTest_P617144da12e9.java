/* @test    @summary Test for Executors.unconfigurableExecutorService method    @run main ExecutorsWitupTest_P617144da12e9 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P617144da12e9 {
    public static void main(String[] args) {
        ExecutorService original = Executors.newSingleThreadExecutor();
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(original);
        if (unconfigurable == null) {
            throw new AssertionError("unconfigurableExecutorService returned null");
        }
        if (unconfigurable == original) {
            throw new AssertionError("Expected a new wrapper, but got the same instance");
        }
        // Test that the returned object is an instance of DelegatedExecutorService
        // Since DelegatedExecutorService is package-private, we test behavior instead
        try {
            unconfigurable.submit(() -> {
                System.out.println("Test task executed");
            }).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to submit task to unconfigurable executor", e);
        }
        // Test that null input throws NullPointerException
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
