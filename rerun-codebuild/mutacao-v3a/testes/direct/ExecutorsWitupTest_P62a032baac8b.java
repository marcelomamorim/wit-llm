/* @test    @summary Test for Executors.unconfigurableExecutorService method    @run main ExecutorsWitupTest_P62a032baac8b */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P62a032baac8b {

    public static void main(String[] args) {
        testUnconfigurableExecutorServiceWithValidExecutor();
        testUnconfigurableExecutorServiceWithNull();
    }

    private static void testUnconfigurableExecutorServiceWithValidExecutor() {
        ExecutorService original = Executors.newSingleThreadExecutor();
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(original);
        boolean isDelegated = unconfigurable instanceof java.util.concurrent.Executors.DelegatedExecutorService;
        if (!isDelegated) {
            throw new AssertionError("Expected instance of DelegatedExecutorService")
        }
        // Verify that the returned executor is not null
        if (unconfigurable == null) {
            throw new AssertionError("Returned ExecutorService should not be null")
        }
    }

    private static void testUnconfigurableExecutorServiceWithNull() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException")
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
