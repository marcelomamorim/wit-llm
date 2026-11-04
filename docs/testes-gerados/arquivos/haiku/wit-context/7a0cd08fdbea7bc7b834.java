/*
 * @test
 * @summary Tests Executors.newFixedThreadPool(int, ThreadFactory) argument validation and basic behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testZeroThreadsRejected();
        testNegativeThreadsRejected();
        testNullThreadFactoryRejected();
        testValidPoolExecutesTasks();
    }

    static void testZeroThreadsRejected() {
        expectIllegalArgument(0);
    }

    static void testNegativeThreadsRejected() {
        expectIllegalArgument(-1);
    }

    static void expectIllegalArgument(int nThreads) {
        ThreadFactory tf = Executors.defaultThreadFactory();
        try {
            ExecutorService es = Executors.newFixedThreadPool(nThreads, tf);
            es.shutdownNow();
            throw new RuntimeException("No IllegalArgumentException for nThreads=" + nThreads);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNullThreadFactoryRejected() {
        try {
            ExecutorService es = Executors.newFixedThreadPool(1, null);
            es.shutdownNow();
            throw new RuntimeException("No NullPointerException for null ThreadFactory");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testValidPoolExecutesTasks() throws Exception {
        final AtomicInteger created = new AtomicInteger();
        ThreadFactory counting = r -> {
            created.incrementAndGet();
            return new Thread(r);
        };
        ExecutorService es = Executors.newFixedThreadPool(2, counting);
        try {
            if (((ThreadPoolExecutor) es).getMaximumPoolSize() != 2) {
                throw new RuntimeException("Unexpected maximum pool size");
            }
            Callable<Integer> task = () -> 42;
            Future<Integer> f = es.submit(task);
            if (f.get() != 42) {
                throw new RuntimeException("Unexpected task result");
            }
            if (created.get() < 1) {
                throw new RuntimeException("ThreadFactory was not used");
            }
        } finally {
            es.shutdown();
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("Pool did not terminate");
            }
        }
    }
}
