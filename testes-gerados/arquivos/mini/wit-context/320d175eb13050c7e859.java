/*
 * @test
 * @summary Test ForkJoinPool(int) constructor for valid and invalid parallelism values
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) {
        testValidParallelism();
        testInvalidParallelismZero();
        testInvalidParallelismNegative();
        testInvalidParallelismTooLarge();
    }

    private static void testValidParallelism() {
        // Test with a valid parallelism value (1)
        ForkJoinPool pool = new ForkJoinPool(1);
        if (pool == null) {
            throw new AssertionError("ForkJoinPool constructor returned null for valid parallelism");
        }
    }

    private static void testInvalidParallelismZero() {
        try {
            new ForkJoinPool(0);
            throw new AssertionError("Expected IllegalArgumentException for parallelism=0");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testInvalidParallelismNegative() {
        try {
            new ForkJoinPool(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative parallelism");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testInvalidParallelismTooLarge() {
        int tooLarge = 0x7fff + 1;
        try {
            new ForkJoinPool(tooLarge);
            throw new AssertionError("Expected IllegalArgumentException for parallelism > 0x7fff");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
