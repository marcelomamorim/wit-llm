/*
 * @test
 * @summary Test default ForkJoinPool() constructor creates pool with expected parallelism
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int parallelism = pool.getParallelism();
        int procs = Runtime.getRuntime().availableProcessors();
        int maxCap = 32767; // MAX_CAP constant from ForkJoinPool source
        int expected = Math.min(maxCap, procs);
        if (parallelism != expected) {
            throw new AssertionError("Expected parallelism " + expected + ", but got " + parallelism);
        }
        pool.shutdown();
        if (!pool.isTerminated()) {
            try {
                pool.awaitTermination(1000, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new AssertionError("Interrupted while waiting for pool termination", e);
            }
        }
    }
}
