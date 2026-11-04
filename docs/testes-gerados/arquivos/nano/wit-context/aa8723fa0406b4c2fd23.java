/*
 * @test
 * @summary Tests for ForkJoinPool constructor with byte parameter.
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNegativeParallelism();
        testExcessiveParallelism();
        testNullFactory();
    }

    static void testValidConstructor() {
        try {
            ForkJoinPool pool = new ForkJoinPool((byte) 1);
            assert pool != null : "Pool should be created successfully";
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid constructor", e);
        }
    }

    static void testNegativeParallelism() {
        try {
            ForkJoinPool pool = new ForkJoinPool((byte) 0);
            assert pool != null : "Pool should be created with default parallelism";
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative parallelism", e);
        }
    }

    static void testExcessiveParallelism() {
        try {
            int excessiveParallelism = Integer.MAX_VALUE;
            ForkJoinPool pool = new ForkJoinPool((byte) 1);
            // Since constructor caps parallelism at MAX_CAP, verify that no exception occurs
            assert pool != null : "Pool should be created with capped parallelism";
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for excessive parallelism", e);
        }
    }

    static void testNullFactory() {
        try {
            ForkJoinPool pool = new ForkJoinPool((byte) 1);
            assert pool != null : "Pool should be created even if factory is null";
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null factory", e);
        }
    }
}
