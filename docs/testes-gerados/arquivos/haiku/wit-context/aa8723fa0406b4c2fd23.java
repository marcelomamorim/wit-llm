/*
 * @test
 * @summary Verifies that the common pool parallelism follows the system property read by the private byte constructor
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
        ForkJoinPool p = ForkJoinPool.commonPool();
        if (p == null) {
            throw new AssertionError("commonPool() returned null");
        }
        if (p != ForkJoinPool.commonPool()) {
            throw new AssertionError("commonPool() must return the same instance");
        }
        if (p.getParallelism() != 2) {
            throw new AssertionError("Expected parallelism 2 but was " + p.getParallelism());
        }
    }
}
