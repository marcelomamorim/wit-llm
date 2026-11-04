/*
 * @test
 * @summary Tests observable behavior of Executors.newFixedThreadPool(int, ThreadFactory)
 * @run main ExecutorsWitupTest
 */

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class CountingFactory implements ThreadFactory {
        final AtomicInteger count = new AtomicInteger();
        final List<Thread> threads = new CopyOnWriteArrayList<>();
        final String prefix;

        CountingFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            int n = count.incrementAndGet();
            Thread t = new Thread(r, prefix + n);
            threads.add(t);
            return t;
        }
    }

    static void testFactoryUsedForAllThreads() throws Exception {
        CountingFactory factory = new CountingFactory("f-");
        ExecutorService es = Executors.newFixedThreadPool(3, factory);
        try {
            CyclicBarrier barrier = new CyclicBarrier(3);
            List<Future<?>> futures = new CopyOnWriteArrayList<>();
            for (int i = 0; i < 3; i++) {
                futures.add(es.submit(() -> {
                    try {
                        barrier.await(10, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
            }
            for (Future<?> f : futures) {
                f.get(10, TimeUnit.SECONDS);
            }
            check(factory.count.get() == 3,
                  "expected 3 threads from factory, got " + factory.count.get());
        } finally {
            es.shutdown();
            check(es.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void testPoolBoundsConcurrency() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(2, new CountingFactory("b-"));
        try {
            AtomicInteger active = new AtomicInteger();
            AtomicInteger maxSeen = new AtomicInteger();
            List<Future<?>> futures = new CopyOnWriteArrayList<>();
            for (int i = 0; i < 6; i++) {
                futures.add(es.submit(() -> {
                    int now = active.incrementAndGet();
                    maxSeen.accumulateAndGet(now, Math::max);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    active.decrementAndGet();
                }));
            }
            for (Future<?> f : futures) {
                f.get(10, TimeUnit.SECONDS);
            }
            check(maxSeen.get() <= 2,
                  "more than 2 concurrent tasks observed: " + maxSeen.get());
            check(maxSeen.get() >= 1, "no task was executed");
        } finally {
            es.shutdown();
            check(es.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void testThreadNamesFromFactory() throws Exception {
        CountingFactory factory = new CountingFactory("witup-");
        ExecutorService es = Executors.newFixedThreadPool(1, factory);
        try {
            Future<String> name = es.submit(() -> Thread.currentThread().getName());
            String threadName = name.get(10, TimeUnit.SECONDS);
            check(threadName.startsWith("witup-"),
                  "task not run on factory thread: " + threadName);
            check(factory.count.get() == 1,
                  "expected 1 factory call, got " + factory.count.get());
        } finally {
            es.shutdown();
            check(es.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");
        }
    }

    static void testNonPositiveSizeRejected() {
        int[] sizes = {0, -1};
        for (int n : sizes) {
            CountingFactory factory = new CountingFactory("n-");
            boolean thrown = false;
            try {
                ExecutorService es = Executors.newFixedThreadPool(n, factory);
                es.shutdownNow();
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for nThreads=" + n);
        }
    }
}
