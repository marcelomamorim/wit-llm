/*
 * @test
 * @summary Behavioral checks for ThreadPoolExecutor.shutdownNow() on empty and busy pools
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testShutdownNowOnEmptyPool();
        testShutdownNowReturnsQueuedTasksAndInterruptsWorker();
        testShutdownNowSecondCallReturnsEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ThreadPoolExecutor newExecutor() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        return new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);
    }

    static void testShutdownNowOnEmptyPool() throws Exception {
        ThreadPoolExecutor executor = newExecutor();
        List<Runnable> remaining = executor.shutdownNow();
        check(remaining != null, "shutdownNow returned null");
        check(remaining.isEmpty(), "expected no pending tasks");
        check(executor.isShutdown(), "executor should be shut down");
        check(executor.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        check(executor.isTerminated(), "executor should be terminated");
    }

    static void testShutdownNowReturnsQueuedTasksAndInterruptsWorker() throws Exception {
        ThreadPoolExecutor executor = newExecutor();
        final CountDownLatch started = new CountDownLatch(1);
        final AtomicBoolean interrupted = new AtomicBoolean(false);
        Runnable blocker = () -> {
            started.countDown();
            try {
                Thread.sleep(60000L);
            } catch (InterruptedException ie) {
                interrupted.set(true);
            }
        };
        Runnable queued1 = () -> { };
        Runnable queued2 = () -> { };

        executor.execute(blocker);
        check(started.await(10, TimeUnit.SECONDS), "blocker task did not start");
        executor.execute(queued1);
        executor.execute(queued2);

        List<Runnable> drained = executor.shutdownNow();
        check(drained.size() == 2, "expected 2 queued tasks but got " + drained.size());
        check(drained.get(0) == queued1, "first drained task mismatch");
        check(drained.get(1) == queued2, "second drained task mismatch");
        check(executor.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        check(interrupted.get(), "running worker was not interrupted");
        check(executor.isTerminated(), "executor should be terminated");
    }

    static void testShutdownNowSecondCallReturnsEmpty() throws Exception {
        ThreadPoolExecutor executor = newExecutor();
        executor.shutdownNow();
        List<Runnable> second = executor.shutdownNow();
        check(second != null, "second shutdownNow returned null");
        check(second.isEmpty(), "second shutdownNow should return no tasks");
        check(executor.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
    }
}
