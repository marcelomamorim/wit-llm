/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.submit(Callable) method
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ExecutionException;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            // Test submit with a Callable that returns a known value
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

            // Test submit with a Callable that throws an exception
            Callable<String> exceptionTask = new Callable<String>() {
                public String call() throws Exception {
                    throw new IllegalStateException("fail");
                }
            };
            Future<String> futureEx = executor.submit(exceptionTask);
            try {
                futureEx.get();
                throw new AssertionError("Expected ExecutionException was not thrown");
            } catch (ExecutionException e) {
                if (!(e.getCause() instanceof IllegalStateException)) {
                    throw new AssertionError("Expected cause to be IllegalStateException but was " + e.getCause());
                }
            }

            // Test submit with a null Callable should throw NullPointerException
            try {
                executor.submit(null);
                throw new AssertionError("Expected NullPointerException was not thrown");
            } catch (NullPointerException npe) {
                // expected
            }

        } finally {
            executor.shutdown();
        }
    }
}
