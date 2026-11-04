/*
 * @test
 * @summary Test submit method of ScheduledThreadPoolExecutor for JDK 11+28
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSubmitReturnsFuture();
        testSubmitWithNullTask();
    }

    public static void testSubmitReturnsFuture() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Callable<String> task = () -> "test";
        Future<String> future = executor.submit(task);
        try {
            String result = future.get();
            if (!"test".equals(result)) {
                throw new AssertionError("Expected 'test', got: " + result);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }

    public static void testSubmitWithNullTask() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.submit((Callable<String>) null);
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException e) {
            // expected
        } finally {
            executor.shutdown();
        }
    }
}
