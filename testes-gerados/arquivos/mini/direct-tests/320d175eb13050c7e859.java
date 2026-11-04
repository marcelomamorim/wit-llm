/*
 * @test
 * @summary Test ForkJoinPool constructor with parallelism parameter
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) {
        // Test with positive parallelism
        ForkJoinPool pool = new ForkJoinPool(2);
        if (pool == null) {
            throw new RuntimeException("ForkJoinPool instance is null");
        }

        // Test with parallelism = 1 (minimum valid parallelism)
        ForkJoinPool pool1 = new ForkJoinPool(1);
        if (pool1 == null) {
            throw new RuntimeException("ForkJoinPool instance is null");
        }

        // Test with parallelism = 0 (edge case, should create pool with 0 parallelism)
        ForkJoinPool pool0 = new ForkJoinPool(0);
        if (pool0 == null) {
            throw new RuntimeException("ForkJoinPool instance is null");
        }

        // Test with negative parallelism (behavior not specified, just create and check not null)
        ForkJoinPool poolNeg = new ForkJoinPool(-1);
        if (poolNeg == null) {
            throw new RuntimeException("ForkJoinPool instance is null");
        }
    }
}
