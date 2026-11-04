/*
 * @test
 * @summary Basic observable behavior of Executors.newWorkStealingPool()
 * @run main ExecutorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService ex = Executors.newWorkStealingPool();
        try {
            check(ex != null, "newWorkStealingPool returned null");
            check(ex instanceof ForkJoinPool, "executor is not a ForkJoinPool");
            check(!ex.isShutdown(), "new pool should not be shut down");
            check(!ex.isTerminated(), "new pool should not be terminated");

            ForkJoinPool fjp = (ForkJoinPool) ex;
            int expected = Runtime.getRuntime().availableProcessors();
            check(fjp.getParallelism() == expected,
                  "parallelism should equal availableProcessors");

            Future<Integer> f = ex.submit(() -> 42);
            check(f.get(10, TimeUnit.SECONDS) == 42, "submitted task result mismatch");

            List<Callable<Integer>> tasks = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                final int v = i;
                tasks.add(() -> v * 2);
            }
            List<Future<Integer>> results = ex.invokeAll(tasks);
            check(results.size() == 16, "invokeAll size mismatch");
            for (int i = 0; i < 16; i++) {
                check(results.get(i).get(10, TimeUnit.SECONDS) == i * 2,
                      "invokeAll result mismatch at index " + i);
            }

            ex.shutdown();
            check(ex.isShutdown(), "pool should be shut down");
            check(ex.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");
            check(ex.isTerminated(), "pool should be terminated");
        } finally {
            ex.shutdownNow();
        }
    }
}
