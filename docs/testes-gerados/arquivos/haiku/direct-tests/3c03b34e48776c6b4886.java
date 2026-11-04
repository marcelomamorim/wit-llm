/*
 * @test
 * @summary Verifies that ScheduledThreadPoolExecutor.execute runs submitted commands promptly on a pool thread and counts them as completed
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testExecuteRunsOnPoolThread();
        testExecuteRunsMultipleCommands();
    }

    static void testExecuteRunsOnPoolThread() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Thread> runner = new AtomicReference<>();
            Thread caller = Thread.currentThread();
            ex.execute(() -> {
                runner.set(Thread.currentThread());
                latch.countDown();
            });
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("command submitted by execute was not run");
            }
            Thread t = runner.get();
            if (t == null) {
                throw new RuntimeException("runner thread not recorded");
            }
            if (t == caller) {
                throw new RuntimeException("command ran on the caller thread");
            }
        } finally {
            ex.shutdown();
            if (!ex.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("executor did not terminate");
            }
        }
    }

    static void testExecuteRunsMultipleCommands() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(2);
        final int n = 3;
        try {
            CountDownLatch latch = new CountDownLatch(n);
            for (int i = 0; i < n; i++) {
                ex.execute(latch::countDown);
            }
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("not all commands were run");
            }
        } finally {
            ex.shutdown();
            if (!ex.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("executor did not terminate");
            }
        }
        if (ex.getCompletedTaskCount() != n) {
            throw new RuntimeException("expected completed count " + n
                    + " but was " + ex.getCompletedTaskCount());
        }
    }
}
