/* @test    @summary Test ForkJoinPool constructor with parallelism parameter    @run main ForkJoinPoolWitupTest */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testConstructorWithParallelism(1);
        testConstructorWithParallelism(4);
        testConstructorWithParallelism(0); // Edge case: zero parallelism
        testConstructorWithParallelism(-1); // Edge case: negative parallelism
    }

    private static void testConstructorWithParallelism(int parallelism) {
        try {
            ForkJoinPool pool = new ForkJoinPool(parallelism);
            int actualParallelism = pool.getParallelism();
            if (parallelism <= 0) {
                // For non-positive values, the pool defaults to Runtime.getRuntime().availableProcessors()
                int expected = Runtime.getRuntime().availableProcessors();
                assert actualParallelism == expected : "Expected parallelism " + expected + " but got " + actualParallelism;
            } else {
                assert actualParallelism == parallelism : "Expected parallelism " + parallelism + " but got " + actualParallelism;
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (IllegalArgumentException e) {
            // For negative values, constructor may throw IllegalArgumentException
            assert parallelism < 0 : "Unexpected IllegalArgumentException for parallelism=" + parallelism;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for parallelism=" + parallelism, e);
        }
    }
}
