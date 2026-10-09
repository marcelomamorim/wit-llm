/*
 * @test
 * @summary Test unconfigurableExecutorService method of Executors
 * @run main ExecutorsWitupTest_P89ed2118a115
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest_P89ed2118a115 {
    public static void main(String[] args) throws Exception {
        testNullArgument();
        testDelegatedExecutorServiceBehavior();
    }

    static void testNullArgument() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testDelegatedExecutorServiceBehavior() throws Exception {
        ExecutorService fixedPool = Executors.newFixedThreadPool(1);
        ExecutorService unconfigurable = Executors.unconfigurableExecutorService(fixedPool);

        // Submit a simple task and check it runs
        unconfigurable.submit(new Runnable() {
            public void run() {
                // no-op
            }
        }).get(); // wait for completion

        // Check that shutdown on unconfigurable delegates to underlying executor
        unconfigurable.shutdown();
        if (!fixedPool.isShutdown()) {
            throw new Error("Underlying executor should be shutdown after unconfigurable.shutdown()");
        }

        // Await termination
        if (!unconfigurable.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new Error("Executor did not terminate in time");
        }
    }
}
