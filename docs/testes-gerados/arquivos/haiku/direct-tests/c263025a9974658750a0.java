/*
 * @test
 * @summary Verify AbortPolicy.rejectedExecution throws RejectedExecutionException with the task and executor in the message
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    static final class NamedTask implements Runnable {
        public void run() { }
        public String toString() { return "MyTask"; }
    }

    public static void main(String[] args) throws Exception {
        testDirectRejectionThrowsWithTaskName();
        testExecuteAfterShutdownIsRejected();
    }

    static void testDirectRejectionThrowsWithTaskName() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            ThreadPoolExecutor.AbortPolicy policy = new ThreadPoolExecutor.AbortPolicy();
            boolean thrown = false;
            try {
                policy.rejectedExecution(new NamedTask(), e);
            } catch (RejectedExecutionException ex) {
                thrown = true;
                String msg = ex.getMessage();
                if (msg == null || !msg.startsWith("Task MyTask rejected from")) {
                    throw new AssertionError("Unexpected message: " + msg);
                }
            }
            if (!thrown) {
                throw new AssertionError("RejectedExecutionException not thrown");
            }
        } finally {
            e.shutdownNow();
        }
    }

    static void testExecuteAfterShutdownIsRejected() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());
        e.shutdown();
        boolean thrown = false;
        try {
            e.execute(new NamedTask());
        } catch (RejectedExecutionException ex) {
            thrown = true;
            String msg = ex.getMessage();
            if (msg == null || !msg.contains("MyTask")) {
                throw new AssertionError("Message does not contain task: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("execute after shutdown did not throw");
        }
    }
}
