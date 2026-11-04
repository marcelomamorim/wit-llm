/*
 * @test
 * @summary WITUP-derived tests for ScheduledThreadPoolExecutor.execute(Runnable)
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCommandThrowsNPE();
        testExecuteRunsTask();
        testExecuteAfterShutdownIsRejected();
    }

    static void testNullCommandThrowsNPE() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            try {
                ex.execute(null);
            } catch (NullPointerException e) {
                return;
            }
            throw new AssertionError("expected NullPointerException for null command");
        } finally {
            ex.shutdownNow();
        }
    }

    static void testExecuteRunsTask() throws InterruptedException {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            CountDownLatch latch = new CountDownLatch(1);
            ex.execute(latch::countDown);
            if (!latch.await(30, TimeUnit.SECONDS)) {
                throw new AssertionError("task submitted via execute was not run");
            }
        } finally {
            ex.shutdownNow();
        }
    }

    static void testExecuteAfterShutdownIsRejected() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        ex.shutdown();
        try {
            try {
                ex.execute(() -> { });
            } catch (RejectedExecutionException e) {
                return;
            }
            throw new AssertionError("expected RejectedExecutionException after shutdown");
        } finally {
            ex.shutdownNow();
        }
    }
}
