/*
 * @test
 * @summary getTaskCount reports completed tasks, the locked running worker and queued tasks
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNewExecutorHasNoTasks() throws Exception {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        check(e.getTaskCount() == 0L, "new executor should have task count 0");
        e.shutdown();
        check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        check(e.getTaskCount() == 0L, "terminated idle executor should have task count 0");
    }

    static void testCompletedTasksCountedAfterTermination() throws Exception {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 3; i++) {
            e.execute(new Runnable() {
                public void run() { }
            });
        }
        e.shutdown();
        check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        check(e.getTaskCount() == 3L,
              "expected 3 completed tasks but got " + e.getTaskCount());
    }

    static void testRunningAndQueuedTasksCounted() throws Exception {
        final CountDownLatch started = new CountDownLatch(1);
        final CountDownLatch release = new CountDownLatch(1);
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        e.execute(new Runnable() {
            public void run() {
                started.countDown();
                try {
                    release.await();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        check(started.await(10, TimeUnit.SECONDS), "first task did not start");
        e.execute(new Runnable() { public void run() { } });
        e.execute(new Runnable() { public void run() { } });
        // running worker (locked) counts 1, two queued tasks count 2
        check(e.getTaskCount() == 3L,
              "expected 3 but got " + e.getTaskCount());
        release.countDown();
        e.shutdown();
        check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        check(e.getTaskCount() == 3L,
              "after termination expected 3 but got " + e.getTaskCount());
    }

    public static void main(String[] args) throws Exception {
        testNewExecutorHasNoTasks();
        testCompletedTasksCountedAfterTermination();
        testRunningAndQueuedTasksCounted();
    }
}
