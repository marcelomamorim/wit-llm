/*
 * @test
 * @summary Argument validation and basic behavior of Executors.newFixedThreadPool
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        int[] invalid = new int[] {0, -1};
        for (int n : invalid) {
            boolean thrown = false;
            try {
                Executors.newFixedThreadPool(n);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException();
            }
        }

        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            if (pool.isShutdown()) {
                throw new RuntimeException();
            }
            Future<Integer> f = pool.submit(new Callable<Integer>() {
                public Integer call() {
                    return 42;
                }
            });
            if (f.get(60, TimeUnit.SECONDS).intValue() != 42) {
                throw new RuntimeException();
            }
        } finally {
            pool.shutdown();
        }
        if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
            throw new RuntimeException();
        }
        if (!pool.isTerminated()) {
            throw new RuntimeException();
        }
    }
}
