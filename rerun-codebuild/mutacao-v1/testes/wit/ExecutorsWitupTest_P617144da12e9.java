/* @test    @summary Test for Executors.unconfigurableExecutorService with null input    @run main ExecutorsWitupTest_P617144da12e9 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P617144da12e9 {

    public static void main(String[] args) {
        testUnconfigurableExecutorServiceNull();
        testUnconfigurableExecutorServiceValid();
    }

    private static void testUnconfigurableExecutorServiceNull() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }

    private static void testUnconfigurableExecutorServiceValid() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(executor);
        if (unconfigurable == null) {
            throw new AssertionError("Returned ExecutorService is null");
        }
        if (unconfigurable == executor) {
            throw new AssertionError("Returned ExecutorService should be a different instance");
        }
        // Additional observable behavior tests could be added here if needed
        // For now, just shutdown to clean up
        unconfigurable.shutdown();
    }
}
