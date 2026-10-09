/*
 * @test
 * @summary Test unconfigurableExecutorService method for null input and normal wrapping
 * @run main ExecutorsWitupTest_P89ed2118a115
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P89ed2118a115 {
    public static void main(String[] args) {
        testNullExecutorThrows();
        testWrapsNonNullExecutor();
    }

    private static void testNullExecutorThrows() {
        try {
            Executors.unconfigurableExecutorService(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWrapsNonNullExecutor() {
        ExecutorService fixedPool = Executors.newFixedThreadPool(1);
        try {
            ExecutorService wrapped = Executors.unconfigurableExecutorService(fixedPool);
            if (wrapped == null) {
                throw new Error("Wrapped ExecutorService is null");
            }
            // Check that wrapped is not the same instance as fixedPool
            if (wrapped == fixedPool) {
                throw new Error("Wrapped ExecutorService should not be the same instance as original");
            }
            // Check that wrapped delegates tasks by submitting a simple task
            final boolean[] ran = {false};
            wrapped.submit(new Runnable() {
                public void run() {
                    ran[0] = true;
                }
            }).join();
            if (!ran[0]) {
                throw new Error("Task submitted to wrapped ExecutorService did not run");
            }
        } finally {
            fixedPool.shutdownNow();
        }
    }
}
