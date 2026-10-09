/* @test
   @summary Test unconfigurableExecutorService method for null input and normal behavior.
   @run main ExecutorsWitupTest_Paafbbfa1521d
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest_Paafbbfa1521d {
    public static void main(String[] args) throws Exception {
        testNullInput();
        testUnconfigurableExecutorService();
    }

    private static void testNullInput() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testUnconfigurableExecutorService() throws Exception {
        ExecutorService original = Executors.newSingleThreadExecutor();
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(original);
        if (unconfigurable == null) {
            throw new AssertionError("unconfigurableExecutorService returned null");
        }
        // Verify that the returned object is an instance of DelegatedExecutorService
        if (!(unconfigurable instanceof java.util.concurrent.Executors.DelegatedExecutorService)) {
            throw new AssertionError("Returned executor is not an instance of DelegatedExecutorService");
        }
        // Submit a simple task and verify execution
        final boolean[] executed = {false};
        unconfigurable.execute(() -> executed[0] = true);
        unconfigurable.shutdown();
        unconfigurable.awaitTermination(1, TimeUnit.SECONDS);
        if (!executed[0]) {
            throw new AssertionError("Task was not executed by unconfigurable executor");
        }
        // Original executor should still be active
        if (original.isShutdown()) {
            throw new AssertionError("Original executor should not be shut down");
        }
    }
}
