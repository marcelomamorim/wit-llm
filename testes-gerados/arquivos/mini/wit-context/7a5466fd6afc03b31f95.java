/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.submit(Callable) for null task and normal usage
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test normal usage: submit a simple callable that returns a string
        Callable<String> task = new Callable<String>() {
            public String call() {
                return "test";
            }
        };

        Future<String> future = executor.submit(task);
        String result = future.get();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }

        // Test submit with null task returns NullPointerException as per current behavior
        // The method calls schedule(task, 0, NANOSECONDS) which throws NPE if task is null
        try {
            executor.submit(null);
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        }

        executor.shutdown();
    }
}
