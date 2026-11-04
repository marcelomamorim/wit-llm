/*
 * @test
 * @summary Witup-derived checks for the default ForkJoinPool constructor
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) throws Exception {
        int expected = Math.min(0x7fff, Runtime.getRuntime().availableProcessors());
        ForkJoinPool pool = new ForkJoinPool();
        try {
            check(pool.getParallelism() == expected,
                  "parallelism " + pool.getParallelism() + " != " + expected);
            check(!pool.isShutdown(), "pool shut down at creation");
            Future<Integer> f = pool.submit((Callable<Integer>) () -> 42);
            check(f.get(60, TimeUnit.SECONDS) == 42, "unexpected task result");
        } finally {
            pool.shutdown();
        }
        check(pool.awaitTermination(60, TimeUnit.SECONDS), "pool did not terminate");
        check(pool.isTerminated(), "pool not terminated after awaitTermination");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
