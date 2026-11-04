/*
 * @test
 * @summary shutdownNow drains queued tasks, interrupts running work, and rejects new submissions
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testQueuedTasksReturnedAndRunningTaskInterrupted();
        testSecondCallReturnsEmptyList();
        testRejectsSubmissionAfterShutdownNow();
    }

    static ThreadPoolExecutor newPool() {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    static CountDownLatch blockWorker(ThreadPoolExecutor ex, CountDownLatch interrupted) throws InterruptedException {
        CountDownLatch started = new CountDownLatch(1);
        ex.execute(() -> {
            started.countDown();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                interrupted.countDown();
            }
        });
        check(started.await(10, TimeUnit.SECONDS), "worker did not start");
        return started;
    }

    static void testQueuedTasksReturnedAndRunningTaskInterrupted() throws Exception {
        ThreadPoolExecutor ex = newPool();
        CountDownLatch interrupted = new CountDownLatch(1);
        blockWorker(ex, interrupted);

        Runnable q1 = () -> { };
        Runnable q2 = () -> { };
        Runnable q3 = () -> { };
        ex.execute(q1);
        ex.execute(q2);
        ex.execute(q3);

        List<Runnable> drained = ex.shutdownNow();
        check(drained.size() == 3, "expected 3 queued tasks, got " + drained.size());
        check(drained.get(0) == q1, "first drained task mismatch");
        check(drained.get(1) == q2, "second drained task mismatch");
        check(drained.get(2) == q3, "third drained task mismatch");
        check(ex.isShutdown(), "pool should be shut down");
        check(interrupted.await(10, TimeUnit.SECONDS), "running task was not interrupted");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");
        check(ex.isTerminated(), "pool should be terminated");
    }

    static void testSecondCallReturnsEmptyList() throws Exception {
        ThreadPoolExecutor ex = newPool();
        CountDownLatch interrupted = new CountDownLatch(1);
        blockWorker(ex, interrupted);
        ex.execute(() -> { });

        List<Runnable> first = ex.shutdownNow();
        check(first.size() == 1, "first call should return 1 task, got " + first.size());

        List<Runnable> second = ex.shutdownNow();
        check(second.isEmpty(), "second call should return empty list");
        check(interrupted.await(10, TimeUnit.SECONDS), "running task was not interrupted");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");
    }

    static void testRejectsSubmissionAfterShutdownNow() throws Exception {
        ThreadPoolExecutor ex = newPool();
        CountDownLatch interrupted = new CountDownLatch(1);
        blockWorker(ex, interrupted);

        ex.shutdownNow();
        check(interrupted.await(10, TimeUnit.SECONDS), "running task was not interrupted");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "pool did not terminate");

        boolean rejected = false;
        try {
            ex.execute(() -> { });
        } catch (RejectedExecutionException e) {
            rejected = true;
        }
        check(rejected, "execute after shutdownNow should be rejected");
    }
}
