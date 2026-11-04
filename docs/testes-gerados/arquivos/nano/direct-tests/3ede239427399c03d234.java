/* @test    @summary Test unconfigurableScheduledExecutorService with a valid ScheduledExecutorService    @run main ExecutorsWitupTest */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testUnconfigurableScheduledExecutorService();
    }

    private static void testUnconfigurableScheduledExecutorService() {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService unconfigurableExecutor = Executors.unconfigurableScheduledExecutorService(scheduledExecutor);
        if (unconfigurableExecutor == null) {
            throw new AssertionError("Returned executor should not be null");
        }
        // Verify that the returned executor is an instance of DelegatedScheduledExecutorService
        boolean isDelegated = false;
        try {
            Class<?> clazz = unconfigurableExecutor.getClass();
            isDelegated = clazz.getName().contains("DelegatedScheduledExecutorService");
        } catch (Throwable t) {
            throw new AssertionError("Failed to determine class of executor", t);
        }
        if (!isDelegated) {
            throw new AssertionError("Returned executor is not of expected DelegatedScheduledExecutorService type");
        }
        // Test that the executor can schedule a task
        try {
            unconfigurableExecutor.schedule(() -> System.out.println("Test task"), 10, TimeUnit.MILLISECONDS).cancel(false);
        } catch (Throwable t) {
            throw new AssertionError("Scheduling task failed", t);
        }
        // Shutdown the executor
        unconfigurableExecutor.shutdown();
        try {
            if (!unconfigurableExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                unconfigurableExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            unconfigurableExecutor.shutdownNow();
        }
    }
}
