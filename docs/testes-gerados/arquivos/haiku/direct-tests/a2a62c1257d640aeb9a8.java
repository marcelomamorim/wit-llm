/*
 * @test
 * @summary Verifies the observable configuration of Executors.newCachedThreadPool() and that its tasks run.
 * @run main ExecutorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService ex = Executors.newCachedThreadPool();
        try {
            check(ex instanceof ThreadPoolExecutor, "expected a ThreadPoolExecutor");
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) ex;

            check(tpe.getCorePoolSize() == 0, "core pool size must be 0");
            check(tpe.getMaximumPoolSize() == Integer.MAX_VALUE, "max pool size must be Integer.MAX_VALUE");
            check(tpe.getKeepAliveTime(TimeUnit.SECONDS) == 60L, "keep-alive must be 60 seconds");
            check(tpe.getQueue() instanceof SynchronousQueue, "queue must be a SynchronousQueue");
            check(!ex.isShutdown(), "new pool must not be shut down");

            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                final int value = i;
                futures.add(ex.submit(() -> value * 10));
            }
            int sum = 0;
            for (Future<Integer> f : futures) {
                sum += f.get(60, TimeUnit.SECONDS);
            }
            check(sum == 100, "unexpected sum of task results: " + sum);
        } finally {
            ex.shutdown();
        }
        check(ex.isShutdown(), "pool must report shutdown after shutdown()");
        check(ex.awaitTermination(60, TimeUnit.SECONDS), "pool did not terminate");
    }
}
