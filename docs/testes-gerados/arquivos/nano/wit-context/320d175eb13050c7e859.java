/*
 * @test
 * @summary Tests for ForkJoinPool constructor with parallelism parameter
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testValidParallelism();
        testZeroParallelism();
        testNegativeParallelism();
        testExcessiveParallelism();
    }

    private static void testValidParallelism() {
        try {
            ForkJoinPool pool = new ForkJoinPool(4);
            if (pool == null) {
                throw new AssertionError("Failed to create ForkJoinPool with valid parallelism");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid parallelism", e);
        }
    }

    private static void testZeroParallelism() {
        try {
            ForkJoinPool pool = new ForkJoinPool(0);
            throw new AssertionError("Expected IllegalArgumentException for zero parallelism");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero parallelism", e);
        }
    }

    private static void testNegativeParallelism() {
        try {
            ForkJoinPool pool = new ForkJoinPool(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative parallelism");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative parallelism", e);
        }
    }

    private static void testExcessiveParallelism() {
        int maxParallelism = Integer.MAX_VALUE;
        try {
            ForkJoinPool pool = new ForkJoinPool(maxParallelism);
            if (pool == null) {
                throw new AssertionError("Failed to create ForkJoinPool with max parallelism");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for max parallelism", e);
        }
    }
}
