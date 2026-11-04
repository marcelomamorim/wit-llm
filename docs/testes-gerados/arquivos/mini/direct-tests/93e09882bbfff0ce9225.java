/*
 * @test
 * @summary Test ForkJoinTask.adapt(Callable) method
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {
    public static void main(String[] args) throws Exception {
        // Test adapt with a Callable that returns a known value
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "testValue";
            }
        };

        ForkJoinTask<String> task = ForkJoinTask.adapt(callable);

        // Fork and join to get the result
        task.fork();
        String result = task.join();

        if (!"testValue".equals(result)) {
            throw new AssertionError("Expected 'testValue' but got: " + result);
        }

        // Test adapt with a Callable that throws checked exception
        Callable<String> exceptionCallable = new Callable<String>() {
            public String call() throws Exception {
                throw new Exception("Callable exception");
            }
        };

        ForkJoinTask<String> exceptionTask = ForkJoinTask.adapt(exceptionCallable);
        exceptionTask.fork();

        try {
            exceptionTask.join();
            throw new AssertionError("Expected exception was not thrown");
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause == null || !"Callable exception".equals(cause.getMessage())) {
                throw new AssertionError("Unexpected exception cause: " + e, e);
            }
        }

        // Test adapt with a Callable that returns null
        Callable<String> nullCallable = new Callable<String>() {
            public String call() {
                return null;
            }
        };

        ForkJoinTask<String> nullTask = ForkJoinTask.adapt(nullCallable);
        nullTask.fork();
        String nullResult = nullTask.join();
        if (nullResult != null) {
            throw new AssertionError("Expected null result but got: " + nullResult);
        }
    }
}
