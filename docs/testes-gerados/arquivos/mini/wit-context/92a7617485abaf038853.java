/*
 * @test
 * @summary Test java.util.concurrent.Executors.newWorkStealingPool(int) for valid and invalid inputs
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class ExecutorsWitupTest {
    public static void main(String[] args) {
        testValidParallelism();
        testZeroParallelism();
        testNegativeParallelism();
        testTooLargeParallelism();
    }

    private static void testValidParallelism() {
        // Test with a valid parallelism value
        int parallelism = 4;
        ExecutorService es = null;
        try {
            es = java.util.concurrent.Executors.newWorkStealingPool(parallelism);
            if (es == null) {
                throw new RuntimeException("newWorkStealingPool returned null for valid parallelism");
            }
            // Check that the returned ExecutorService is a ForkJoinPool
            if (!(es instanceof ForkJoinPool)) {
                throw new RuntimeException("Returned ExecutorService is not a ForkJoinPool");
            }
        } finally {
            if (es != null) {
                es.shutdown();
            }
        }
    }

    private static void testZeroParallelism() {
        // According to source, parallelism <= 0 should throw IllegalArgumentException
        try {
            java.util.concurrent.Executors.newWorkStealingPool(0);
            throw new RuntimeException("Expected IllegalArgumentException for parallelism=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNegativeParallelism() {
        // Negative parallelism should throw IllegalArgumentException
        try {
            java.util.concurrent.Executors.newWorkStealingPool(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative parallelism");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testTooLargeParallelism() {
        // Parallelism greater than 0x7fff (32767) should throw IllegalArgumentException
        int tooLarge = 0x7fff + 1;
        try {
            java.util.concurrent.Executors.newWorkStealingPool(tooLarge);
            throw new RuntimeException("Expected IllegalArgumentException for too large parallelism");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
