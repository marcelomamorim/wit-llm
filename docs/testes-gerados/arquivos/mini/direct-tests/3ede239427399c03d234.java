/*
 * @test
 * @summary Test unconfigurableScheduledExecutorService method of Executors
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNullArgument();
        testDelegatedScheduledExecutorServiceBehavior();
    }

    static void testNullArgument() {
        try {
            Executors.unconfigurableScheduledExecutorService(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testDelegatedScheduledExecutorServiceBehavior() throws Exception {
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService unconfigurable = Executors.unconfigurableScheduledExecutorService(scheduled);

        // Schedule a callable to return a string after 100 ms
        ScheduledFuture<String> future = unconfigurable.schedule(new Callable<String>() {
            public String call() {
                return "test";
            }
        }, 100, TimeUnit.MILLISECONDS);

        String result = future.get();
        if (!"test".equals(result)) {
            throw new RuntimeException("Unexpected result from scheduled task: " + result);
        }

        // Test that shutdown delegates correctly
        unconfigurable.shutdown();
        if (!unconfigurable.isShutdown()) {
            throw new RuntimeException("Executor should be shutdown after shutdown() call");
        }

        // Cleanup underlying executor
        scheduled.shutdownNow();
    }
}
