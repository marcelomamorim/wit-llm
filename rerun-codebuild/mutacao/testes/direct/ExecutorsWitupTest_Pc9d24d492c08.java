/* @test    @summary Test for Executors.unconfigurableExecutorService method    @run main ExecutorsWitupTest_Pc9d24d492c08 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_Pc9d24d492c08 {

    public static void main(String[] args) {
        testUnconfigurableExecutorServiceWithValidExecutor();
        testUnconfigurableExecutorServiceWithNull();
    }

    private static void testUnconfigurableExecutorServiceWithValidExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(executor);
        if (unconfigurable == null) {
            throw new AssertionError("Expected non-null ExecutorService");
        }
        if (unconfigurable.getClass().getName().contains("DelegatedExecutorService")) {
            // Expected class name pattern
        } else {
            throw new AssertionError("Unexpected class: " + unconfigurable.getClass().getName());
        }
    }

    private static void testUnconfigurableExecutorServiceWithNull() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
