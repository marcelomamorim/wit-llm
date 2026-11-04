/*
 * @test
 * @summary Verifies newCachedThreadPool(ThreadFactory) returns a usable executor that uses the supplied factory and rejects a null factory
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        final AtomicInteger created = new AtomicInteger();
        ThreadFactory tf = r -> {
            created.incrementAndGet();
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        };

        ExecutorService es = Executors.newCachedThreadPool(tf);
        check(es != null, "newCachedThreadPool returned null");
        check(!es.isShutdown(), "new executor should not be shut down");

        Callable<Integer> task = () -> 42;
        Future<Integer> f = es.submit(task);
        check(f.get(30, TimeUnit.SECONDS) == 42, "task result mismatch");
        check(created.get() >= 1, "supplied ThreadFactory was not used");

        es.shutdown();
        check(es.awaitTermination(30, TimeUnit.SECONDS), "executor did not terminate");
        check(es.isTerminated(), "executor should be terminated");

        boolean npe = false;
        try {
            Executors.newCachedThreadPool((ThreadFactory) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null ThreadFactory should be rejected with NullPointerException");
    }
}
