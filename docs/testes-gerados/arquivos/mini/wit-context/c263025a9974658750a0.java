/*
 * @test
 * @summary Test ThreadPoolExecutor.rejectedExecution throws RejectedExecutionException with correct message
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        // Create a dummy Runnable
        Runnable r = new Runnable() {
            public void run() {}
            public String toString() { return "DummyRunnable"; }
        };

        // Create a ThreadPoolExecutor instance with minimal config
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));

        // Create an instance of the handler to test rejectedExecution
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                // Delegate to ThreadPoolExecutor's rejectedExecution method
                e.rejectedExecution(r, e);
            }
        };

        // Test that rejectedExecution throws RejectedExecutionException
        try {
            handler.rejectedExecution(r, executor);
            throw new RuntimeException("Expected RejectedExecutionException not thrown");
        } catch (RejectedExecutionException ex) {
            String msg = ex.getMessage();
            if (!msg.contains("DummyRunnable") || !msg.contains(executor.toString())) {
                throw new RuntimeException("Exception message does not contain expected substrings: " + msg);
            }
        }
    }
}
