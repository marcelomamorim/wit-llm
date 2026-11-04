/*
 * @test
 * @summary Test basic behaviors of FutureTask.get() including normal completion and exception paths
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testGetNormalCompletion();
        testGetExecutionException();
        testGetCancellationException();
        System.out.println("All tests passed.");
    }

    // Test that get() returns the correct result when the task completes normally
    private static void testGetNormalCompletion() throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "success";
            }
        };
        FutureTask<String> ft = new FutureTask<>(callable);
        Thread t = new Thread(ft);
        t.start();
        String result = ft.get();
        if (!"success".equals(result)) {
            throw new AssertionError("Expected 'success' but got: " + result);
        }
    }

    // Test that get() throws ExecutionException when the callable throws an exception
    private static void testGetExecutionException() throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                throw new IllegalStateException("fail");
            }
        };
        FutureTask<String> ft = new FutureTask<>(callable);
        Thread t = new Thread(ft);
        t.start();
        try {
            ft.get();
            throw new AssertionError("Expected ExecutionException was not thrown");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalStateException)) {
                throw new AssertionError("Expected cause to be IllegalStateException but was " + cause);
            }
            if (!"fail".equals(cause.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + cause.getMessage());
            }
        }
    }

    // Test that get() throws CancellationException if the task was cancelled
    private static void testGetCancellationException() throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000);
                return "done";
            }
        };
        FutureTask<String> ft = new FutureTask<>(callable);
        Thread t = new Thread(ft);
        t.start();
        boolean cancelled = ft.cancel(true);
        if (!cancelled) {
            throw new AssertionError("Failed to cancel the FutureTask");
        }
        try {
            ft.get();
            throw new AssertionError("Expected CancellationException was not thrown");
        } catch (CancellationException e) {
            // Expected
        }
    }
}
