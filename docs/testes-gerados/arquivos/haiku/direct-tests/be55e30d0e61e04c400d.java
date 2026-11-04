/*
 * @test
 * @summary Verifies ThreadPoolExecutor.toString() reports run state, pool size, active, queued and completed task counts
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testIdleRunning();
        testTerminatedAfterCompletion();
        testQueuedAndActive();
    }

    static void testIdleRunning() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            String s = e.toString();
            check(s.startsWith("java.util.concurrent.ThreadPoolExecutor@"), "prefix: " + s);
            check(s.endsWith("[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]"),
                  "idle: " + s);
        } finally {
            e.shutdown();
        }
    }

    static void testTerminatedAfterCompletion() throws InterruptedException {
        ThreadPoolExecutor e = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 3; i++) {
            e.execute(() -> { });
        }
        e.shutdown();
        check(e.awaitTermination(30, TimeUnit.SECONDS), "not terminated");
        String s = e.toString();
        check(s.endsWith("[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 3]"),
              "terminated: " + s);
    }

    static void testQueuedAndActive() throws InterruptedException {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        CountDownLatch started = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        e.execute(() -> {
            started.countDown();
            try {
                release.await();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        });
        started.await();
        e.execute(() -> { });
        e.execute(() -> { });
        String s = e.toString();
        check(s.endsWith("[Running, pool size = 1, active threads = 1, queued tasks = 2, completed tasks = 0]"),
              "busy: " + s);
        release.countDown();
        e.shutdown();
        check(e.awaitTermination(30, TimeUnit.SECONDS), "not terminated (busy)");
        s = e.toString();
        check(s.endsWith("[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 3]"),
              "final: " + s);
    }
}
