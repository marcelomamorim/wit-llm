/* @test    @summary Test for Executors.unconfigurableExecutorService to cover null input case    @run main ExecutorsWitupTest_P62a032baac8b */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P62a032baac8b {

    public static void main(String[] args) {
        testUnconfigurableExecutorServiceWithNull();
        testUnconfigurableExecutorServiceWithValid();
    }

    public static void testUnconfigurableExecutorServiceWithNull() {
        try {
            Executors.unconfigurableExecutorService(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }

    public static void testUnconfigurableExecutorServiceWithValid() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(executor);
        if (unconfigurable == null) {
            throw new AssertionError("Returned ExecutorService is null");
        }
        // Additional observable behavior: submit a task and verify execution
        final boolean[] taskExecuted = {false};
        unconfigurable.submit(() -> {
            taskExecuted[0] = true;
        });
        // Wait briefly for task to execute
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!taskExecuted[0]) {
            throw new AssertionError("Task was not executed in unconfigurableExecutorService");
        }
        // Shutdown executors to clean up
        unconfigurable.shutdown();
        executor.shutdown();
    }
}
