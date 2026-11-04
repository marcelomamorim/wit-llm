/*
 * @test
 * @summary Verify ThreadPoolExecutor.setThreadFactory rejects null and applies non-null factories observably
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testNullDoesNotReplaceCurrentFactory();
        testNonNullFactoryIsUsedByExecute();
    }

    static ThreadPoolExecutor newExecutor(ThreadFactory factory) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue, factory);
    }

    static void runOneTask(ThreadPoolExecutor executor) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1);
        executor.execute(() -> done.countDown());
        if (!done.await(30, TimeUnit.SECONDS)) {
            throw new RuntimeException(task did not complete);
        }
    }

    static void testNullThrowsNullPointerException() {
        ThreadPoolExecutor executor = newExecutor(r -> new Thread(r));
        try {
            executor.setThreadFactory(null);
            throw new RuntimeException(expected NullPointerException);
        } catch (NullPointerException expected) {
            // expected
        } finally {
            executor.shutdown();
        }
    }

    static void testNullDoesNotReplaceCurrentFactory() throws InterruptedException {
        AtomicInteger count = new AtomicInteger();
        ThreadFactory original = r -> {
            count.incrementAndGet();
            return new Thread(r);
        };
        ThreadPoolExecutor executor = newExecutor(original);
        try {
            try {
                executor.setThreadFactory(null);
                throw new RuntimeException(expected NullPointerException);
            } catch (NullPointerException expected) {
                // expected
            }
            runOneTask(executor);
            if (count.get() != 1) {
                throw new RuntimeException(original factory not used after rejected null: + count.get());
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    static void testNonNullFactoryIsUsedByExecute() throws InterruptedException {
        AtomicInteger initialCount = new AtomicInteger();
        AtomicInteger newCount = new AtomicInteger();
        ThreadPoolExecutor executor = newExecutor(r -> {
            initialCount.incrementAndGet();
            return new Thread(r);
        });
        try {
            executor.setThreadFactory(r -> {
                newCount.incrementAndGet();
                return new Thread(r);
            });
            runOneTask(executor);
            if (newCount.get() != 1) {
                throw new RuntimeException(new factory not used: + newCount.get());
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        }
    }
}
