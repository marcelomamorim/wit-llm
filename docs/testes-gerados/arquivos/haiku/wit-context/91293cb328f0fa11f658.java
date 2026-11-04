/*
 * @test
 * @summary Verifies that Executors.newWorkStealingPool() returns a usable ForkJoinPool with parallelism equal to the available processors
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newWorkStealingPool();
        try {
            if (pool == null) {
                throw new AssertionError("newWorkStealingPool returned null");
            }
            if (!(pool instanceof ForkJoinPool)) {
                throw new AssertionError("Expected ForkJoinPool but got " + pool.getClass().getName());
            }
            if (pool.isShutdown()) {
                throw new AssertionError("New pool must not be shut down");
            }
            int expected = Runtime.getRuntime().availableProcessors();
            int actual = ((ForkJoinPool) pool).getParallelism();
            if (actual != expected) {
                throw new AssertionError("Parallelism expected " + expected + " but was " + actual);
            }
            Future<Integer> f = pool.submit(() -> 42);
            Integer result = f.get();
            if (result.intValue() != 42) {
                throw new AssertionError("Unexpected task result: " + result);
            }
        } finally {
            pool.shutdown();
        }
        if (!pool.isShutdown()) {
            throw new AssertionError("Pool should be shut down after shutdown()");
        }
    }
}
