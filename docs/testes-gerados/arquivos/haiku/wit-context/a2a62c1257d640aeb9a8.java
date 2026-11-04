/*
 * @test
 * @summary Basic observable behavior of Executors.newCachedThreadPool()
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newCachedThreadPool();
        try {
            check(es != null, "newCachedThreadPool returned null");
            check(es instanceof ThreadPoolExecutor, "expected a ThreadPoolExecutor");
            check(!es.isShutdown(), "new pool must not be shut down");

            ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
            check(tpe.getCorePoolSize() == 0, "core pool size must be 0");
            check(tpe.getMaximumPoolSize() == Integer.MAX_VALUE,
                  "maximum pool size must be Integer.MAX_VALUE");

            Future<Integer> f1 = es.submit(() -> 42);
            check(f1.get(10, TimeUnit.SECONDS) == 42, "unexpected result for first task");

            Future<Integer> f2 = es.submit(() -> 7);
            check(f2.get(10, TimeUnit.SECONDS) == 7, "unexpected result for second task");

            es.shutdown();
            check(es.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");
            check(es.isTerminated(), "pool must be terminated");
        } finally {
            es.shutdownNow();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
