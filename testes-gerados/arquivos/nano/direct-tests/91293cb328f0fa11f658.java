/* @test    @summary Test Executors.newWorkStealingPool() method for correct behavior    @run main ExecutorsWitupTest */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNewWorkStealingPoolReturnsForkJoinPool();
    }

    private static void testNewWorkStealingPoolReturnsForkJoinPool() {
        ExecutorService executor = Executors.newWorkStealingPool();
        if (!(executor instanceof ForkJoinPool)) {
            throw new AssertionError("Expected instance of ForkJoinPool, but got: " + executor.getClass());
        }
        ForkJoinPool fjPool = (ForkJoinPool) executor;
        int expectedParallelism = Runtime.getRuntime().availableProcessors();
        if (fjPool.getParallelism() != expectedParallelism) {
            throw new AssertionError("Expected parallelism: " + expectedParallelism + ", but got: " + fjPool.getParallelism());
        }
        // Additional checks can be added here if needed
        System.out.println("testNewWorkStealingPoolReturnsForkJoinPool passed.");
    }
}
