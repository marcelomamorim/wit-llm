/*
 * @test
 * @summary Test for Executors.unconfigurableExecutorService method.
 * @run main ExecutorsWitupTest_Pc9d24d492c08
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_Pc9d24d492c08 {

    public static void main(String[] args) {
        testNullExecutor();
        testValidExecutor();
    }

    private static void testNullExecutor() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testValidExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(executor);
        if (unconfigurable == null) {
            throw new AssertionError("Returned executor should not be null");
        }
        // Additional behavior tests could be added here if needed
        // For now, just shutdown to clean up
        unconfigurable.shutdown();
    }
}
