/*
 * @test
 * @summary Verifies Executors.newCachedThreadPool(ThreadFactory) uses the supplied factory and allows concurrent task execution
 * @run main ExecutorsWitupTest
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsWitupTest {

    private static final long TIMEOUT_SECONDS = 30;

    public static void main(String[] args) throws Exception {
        testTasksRunOnFactoryThreads();
        testConcurrentTasksUseSeparateThreads();
        testReturnedPoolConfiguration();
    }

    static void testTasksRunOnFactoryThreads() throws Exception {
        final AtomicInteger count = new AtomicInteger();
        final Set<Thread> created = Collections.synchronizedSet(new HashSet<Thread>());
        ThreadFactory factory = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                int n = count.incrementAndGet();
                Thread t = new Thread(r, "witup-" + n);
                created.add(t);
                return t;
            }
        };
        ExecutorService es = Executors.newCachedThreadPool(factory);
        try {
            Callable<Thread> task = new Callable<Thread>() {
                public Thread call() {
                    return Thread.currentThread();
                }
            };
            Future<Thread> f = es.submit(task);
            Thread ran = f.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            check(count.get() >= 1, "factory was not used");
            check(created.contains(ran), "task did not run on a factory-created thread");
        } finally {
            es.shutdown();
            check(es.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void testConcurrentTasksUseSeparateThreads() throws Exception {
        final AtomicInteger count = new AtomicInteger();
        ThreadFactory factory = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                count.incrementAndGet();
                return new Thread(r);
            }
        };
        ExecutorService es = Executors.newCachedThreadPool(factory);
        try {
            final CountDownLatch ready = new CountDownLatch(2);
            Callable<Thread> task = new Callable<Thread>() {
                public Thread call() throws Exception {
                    ready.countDown();
                    check(ready.await(TIMEOUT_SECONDS, TimeUnit.SECONDS),
                          "tasks were not executed concurrently");
                    return Thread.currentThread();
                }
            };
            Future<Thread> f1 = es.submit(task);
            Future<Thread> f2 = es.submit(task);
            Thread t1 = f1.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Thread t2 = f2.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            check(t1 != t2, "concurrent tasks shared the same thread");
            check(count.get() == 2, "expected 2 threads created, got " + count.get());
        } finally {
            es.shutdown();
            check(es.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void testReturnedPoolConfiguration() throws Exception {
        ThreadFactory factory = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        ExecutorService es = Executors.newCachedThreadPool(factory);
        try {
            check(es instanceof ThreadPoolExecutor, "unexpected executor type");
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
            check(tpe.getCorePoolSize() == 0, "core pool size should be 0");
            check(tpe.getMaximumPoolSize() == Integer.MAX_VALUE, "maximum pool size should be Integer.MAX_VALUE");
        } finally {
            es.shutdown();
            check(es.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
