/*
 * @test
 * @summary Test newWorkStealingPool method of Executors for expected behavior and exception safety
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.lang.Runtime;

public class ExecutorsWitupTest {
    public static void main(String[] args) {
        testNewWorkStealingPoolReturnsNonNullExecutorService();
        testNewWorkStealingPoolUsesAvailableProcessors();
    }

    private static void testNewWorkStealingPoolReturnsNonNullExecutorService() {
        ExecutorService es = java.util.concurrent.Executors.newWorkStealingPool();
        if (es == null) {
            throw new AssertionError("newWorkStealingPool() returned null");
        }
        // Check that returned ExecutorService is a ForkJoinPool
        if (!(es instanceof ForkJoinPool)) {
            throw new AssertionError("newWorkStealingPool() did not return a ForkJoinPool instance");
        }
        es.shutdown();
    }

    private static void testNewWorkStealingPoolUsesAvailableProcessors() {
        int procs = Runtime.getRuntime().availableProcessors();
        if (procs <= 0 || procs > 0x7fff) {
            // According to WIT, this could cause IllegalArgumentException in ForkJoinPool constructor
            // But since we cannot set availableProcessors, we just note this case and skip throwing
            // This test is limited by environment and does not attempt to provoke exception
            return;
        }
        ExecutorService es = java.util.concurrent.Executors.newWorkStealingPool();
        ForkJoinPool fjp = (ForkJoinPool) es;
        int parallelism = fjp.getParallelism();
        if (parallelism != procs) {
            throw new AssertionError("ForkJoinPool parallelism " + parallelism + " does not match availableProcessors " + procs);
        }
        es.shutdown();
    }
}
