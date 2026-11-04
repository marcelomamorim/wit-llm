/*
 * @test
 * @summary ThreadPoolExecutor.setThreadFactory rejects null and replaces the factory used for new workers
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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ThreadPoolExecutor newExecutor(ThreadFactory factory) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue, factory);
    }

    static void testNullThrowsNullPointerException() {
        ThreadFactory original = Thread::new;
        ThreadPoolExecutor tpe = newExecutor(original);
        try {
            tpe.setThreadFactory(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            tpe.shutdownNow();
        }
    }

    static void testNullDoesNotReplaceFactory() {
        ThreadFactory original = Thread::new;
        ThreadPoolExecutor tpe = newExecutor(original);
        try {
            try {
                tpe.setThreadFactory(null);
            } catch (NullPointerException expected) {
                // expected
            }
            check(tpe.getThreadFactory() == original,
                  "factory changed after rejected null");
        } finally {
            tpe.shutdownNow();
        }
    }

    static void testSetReplacesFactory() {
        ThreadPoolExecutor tpe = newExecutor(Thread::new);
        ThreadFactory replacement = Thread::new;
        try {
            tpe.setThreadFactory(replacement);
            check(tpe.getThreadFactory() == replacement,
                  "factory not replaced");
        } finally {
            tpe.shutdownNow();
        }
    }

    static void testReplacedFactoryCreatesWorker() throws InterruptedException {
        ThreadPoolExecutor tpe = newExecutor(Thread::new);
        AtomicInteger created = new AtomicInteger();
        CountDownLatch ran = new CountDownLatch(1);
        ThreadFactory counting = r -> {
            created.incrementAndGet();
            return new Thread(r);
        };
        try {
            tpe.setThreadFactory(counting);
            tpe.execute(ran::countDown);
            check(ran.await(30, TimeUnit.SECONDS), "task did not run");
            check(created.get() == 1,
                  "expected replaced factory to create 1 thread, got " + created.get());
        } finally {
            tpe.shutdownNow();
        }
    }

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testNullDoesNotReplaceFactory();
        testSetReplacesFactory();
        testReplacedFactoryCreatesWorker();
    }
}
