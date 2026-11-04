/*
 * @test
 * @summary Checks that ThreadPoolExecutor.AbortPolicy.rejectedExecution throws RejectedExecutionException with the task and executor description
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    static final class NamedTask implements Runnable {
        public void run() { }
        public String toString() { return "NamedTask-1"; }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testAbortPolicyDirectCall() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            NamedTask t = new NamedTask();
            String expectedExecutor = e.toString();
            boolean thrown = false;
            try {
                new ThreadPoolExecutor.AbortPolicy().rejectedExecution(t, e);
            } catch (RejectedExecutionException ex) {
                thrown = true;
                String msg = ex.getMessage();
                String expected = "Task NamedTask-1 rejected from " + expectedExecutor;
                if (!expected.equals(msg)) {
                    fail("Unexpected message: " + msg);
                }
            }
            if (!thrown) {
                fail("AbortPolicy did not throw RejectedExecutionException");
            }
        } finally {
            e.shutdownNow();
        }
    }

    static void testDefaultHandlerOnShutdownPool() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        e.shutdown();
        boolean thrown = false;
        try {
            e.execute(new NamedTask());
        } catch (RejectedExecutionException ex) {
            thrown = true;
            String msg = ex.getMessage();
            if (msg == null || !msg.startsWith("Task NamedTask-1 rejected from ")) {
                fail("Unexpected message: " + msg);
            }
        }
        if (!thrown) {
            fail("Default handler did not reject task on shut down executor");
        }
    }

    public static void main(String[] args) {
        testAbortPolicyDirectCall();
        testDefaultHandlerOnShutdownPool();
    }
}
