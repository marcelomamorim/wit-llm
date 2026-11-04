/* @test    @summary Test rejectedExecution method of ThreadPoolExecutor for rejection handling    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionException;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testRejectedExecutionThrowsException();
    }

    public static void testRejectedExecutionThrowsException() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            executor.rejectedExecution(task, executor);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected RejectedExecutionException was not thrown");
        } catch (RejectedExecutionException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Task") || !message.contains("rejected from")) {
                throw new AssertionError("Exception message does not contain expected substrings: " + message);
            }
        }
    }
}
