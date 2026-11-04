/*
 * @test
 * @summary Verifies the default ForkJoinPool() constructor sets parallelism from availableProcessors, uses sync mode, and runs submitted tasks
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        int cpus = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool();
        try {
            int p = pool.getParallelism();
            check(p >= 1, "parallelism must be >= 1, was " + p);
            check(p <= cpus, "parallelism must be <= availableProcessors, was " + p);
            check(!pool.getAsyncMode(), "default constructor must use sync mode");
            check(!pool.isShutdown(), "new pool must not be shut down");

            Integer result = pool.submit(() -> 42).get();
            check(result == 42, "submitted task result mismatch: " + result);
        } finally {
            pool.shutdown();
            check(pool.awaitTermination(60, TimeUnit.SECONDS), "pool did not terminate");
        }
        check(pool.isTerminated(), "pool must be terminated after shutdown");
    }
}
