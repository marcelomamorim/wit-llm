/*
 * @test
 * @summary Verifies setRejectedExecutionHandler rejects null and uses the configured handler on rejection
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullHandlerThrowsNullPointerException();
        testConfiguredHandlerInvokedOnRejection();
        testReplacedHandlerIsUsed();
    }

    static void testNullHandlerThrowsNullPointerException() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            e.setRejectedExecutionHandler(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            e.shutdownNow();
        }
    }

    static void saturate(ThreadPoolExecutor e, CountDownLatch release) throws InterruptedException {
        CountDownLatch started = new CountDownLatch(1);
        e.execute(() -> {
            started.countDown();
            try {
                release.await();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        started.await();
        e.execute(() -> { });
    }

    static void testConfiguredHandlerInvokedOnRejection() throws InterruptedException {
        AtomicInteger calls = new AtomicInteger();
        Runnable[] rejected = new Runnable[1];
        CountDownLatch release = new CountDownLatch(1);
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        e.setRejectedExecutionHandler((r, ex) -> {
            calls.incrementAndGet();
            rejected[0] = r;
        });
        try {
            saturate(e, release);
            Runnable task = () -> { };
            e.execute(task);
            if (calls.get() != 1) {
                throw new AssertionError("handler calls: " + calls.get());
            }
            if (rejected[0] != task) {
                throw new AssertionError("handler received unexpected task");
            }
        } finally {
            release.countDown();
            e.shutdown();
            if (!e.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new AssertionError("executor did not terminate");
            }
        }
    }

    static void testReplacedHandlerIsUsed() throws InterruptedException {
        AtomicInteger first = new AtomicInteger();
        AtomicInteger second = new AtomicInteger();
        CountDownLatch release = new CountDownLatch(1);
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        RejectedExecutionHandler h1 = (r, ex) -> first.incrementAndGet();
        RejectedExecutionHandler h2 = (r, ex) -> second.incrementAndGet();
        e.setRejectedExecutionHandler(h1);
        e.setRejectedExecutionHandler(h2);
        try {
            saturate(e, release);
            e.execute(() -> { });
            if (first.get() != 0) {
                throw new AssertionError("replaced handler was invoked");
            }
            if (second.get() != 1) {
                throw new AssertionError("current handler calls: " + second.get());
            }
        } finally {
            release.countDown();
            e.shutdown();
            if (!e.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new AssertionError("executor did not terminate");
            }
        }
    }
}
