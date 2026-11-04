/*
 * @test
 * @summary Test ThreadPoolExecutor.rejectedExecution throws RejectedExecutionException with correct message
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        // Create a ThreadPoolExecutor instance for testing
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));

        // Create a simple Runnable with overridden toString
        Runnable task = new Runnable() {
            public void run() {}
            public String toString() { return "TestTask"; }
        };

        try {
            // Call rejectedExecution and expect RejectedExecutionException
            executor.rejectedExecution(task, executor);
            throw new Error("Expected RejectedExecutionException not thrown");
        } catch (RejectedExecutionException ex) {
            String msg = ex.getMessage();
            if (!msg.contains("TestTask") || !msg.contains(executor.toString())) {
                throw new Error("Exception message does not contain expected substrings: " + msg);
            }
        }

        // Also test with a different Runnable to verify message content
        Runnable anotherTask = new Runnable() {
            public void run() {}
            public String toString() { return "AnotherTask"; }
        };

        try {
            executor.rejectedExecution(anotherTask, executor);
            throw new Error("Expected RejectedExecutionException not thrown");
        } catch (RejectedExecutionException ex) {
            String msg = ex.getMessage();
            if (!msg.contains("AnotherTask") || !msg.contains(executor.toString())) {
                throw new Error("Exception message does not contain expected substrings: " + msg);
            }
        }
    }
}
