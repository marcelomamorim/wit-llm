/*
 * @test
 * @summary Verifies ForkJoinPool(int parallelism) keeps the requested parallelism and runs submitted work
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        int[] parallelismValues = {1, 2, 4};
        for (int p : parallelismValues) {
            ForkJoinPool pool = new ForkJoinPool(p);
            try {
                check(pool.getParallelism() == p,
                      "getParallelism() expected " + p + " but was " + pool.getParallelism());
                check(!pool.isShutdown(), "new pool must not be shut down, p=" + p);

                Future<Integer> f = pool.submit((Callable<Integer>) () -> p * 10);
                int result = f.get();
                check(result == p * 10,
                      "task result expected " + (p * 10) + " but was " + result);
            } finally {
                pool.shutdown();
                check(pool.isShutdown(), "pool must be shut down after shutdown(), p=" + p);
                check(pool.awaitTermination(60, TimeUnit.SECONDS),
                      "pool did not terminate, p=" + p);
                check(pool.isTerminated(), "pool must be terminated, p=" + p);
            }
        }
    }
}
