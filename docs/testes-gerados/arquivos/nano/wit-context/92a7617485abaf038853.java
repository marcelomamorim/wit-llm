/*
 * @test
 * @summary Tests for Executors.newWorkStealingPool(int)
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testValidParallelism();
        testZeroParallelism();
        testNegativeParallelism();
        testExcessiveParallelism();
    }

    static void testValidParallelism() {
        int validParallelism = 4;
        ExecutorService executor = Executors.newWorkStealingPool(validParallelism);
        if (!(executor instanceof ForkJoinPool)) {
            throw new AssertionError("Expected ForkJoinPool instance");
        }
        ForkJoinPool fjPool = (ForkJoinPool) executor;
        if (fjPool.getParallelism() != validParallelism) {
            throw new AssertionError("Parallelism mismatch");
        }
        executor.shutdown();
    }

    static void testZeroParallelism() {
        try {
            Executors.newWorkStealingPool(0);
            throw new AssertionError("Expected NullPointerException or IllegalArgumentException");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testNegativeParallelism() {
        try {
            Executors.newWorkStealingPool(-1);
            throw new AssertionError("Expected NullPointerException or IllegalArgumentException");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testExcessiveParallelism() {
        int excessiveParallelism = 70000;
        try {
            Executors.newWorkStealingPool(excessiveParallelism);
            // Depending on implementation, may throw IllegalArgumentException
        } catch (IllegalArgumentException e) {
            // Expected exception for too large value
        }
    }
}
