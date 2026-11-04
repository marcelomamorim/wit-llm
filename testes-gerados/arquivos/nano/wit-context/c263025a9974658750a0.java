/* @test    @summary Test rejectedExecution method of ThreadPoolExecutor for RejectedExecutionException handling    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionException;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testRejectedExecutionThrowsException();
    }

    public static void testRejectedExecutionThrowsException() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null) {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                super.rejectedExecution(r, e);
            }
        };
        Runnable dummyRunnable = new Runnable() {
            @Override
            public void run() {
                // no-op
            }
        };
        try {
            executor.rejectedExecution(dummyRunnable, executor);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected RejectedExecutionException was not thrown.");
        } catch (RejectedExecutionException e) {
            // Expected exception caught, test passes
        }
    }
}
