/*
 * @test
 * @summary Test AbstractExecutorService.submit(Callable) method behavior
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;

public class AbstractExecutorServiceWitupTest {

    static class SimpleExecutor extends AbstractExecutorService {
        private volatile boolean shutdown = false;

        public void execute(Runnable command) {
            // Run command synchronously in current thread
            command.run();
        }

        public void shutdown() {
            shutdown = true;
        }

        public java.util.List<Runnable> shutdownNow() {
            shutdown = true;
            return java.util.Collections.emptyList();
        }

        public boolean isShutdown() {
            return shutdown;
        }

        public boolean isTerminated() {
            return shutdown;
        }

        public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException {
            return shutdown;
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleExecutor executor = new SimpleExecutor();

        // Test 1: submit null Callable should throw NullPointerException
        try {
            executor.submit(null);
            throw new RuntimeException("Expected NullPointerException for null Callable");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: submit Callable that returns a value
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "testValue";
            }
        };

        Future<String> future = executor.submit(callable);
        if (future == null) {
            throw new RuntimeException("submit returned null Future");
        }
        String result = future.get();
        if (!"testValue".equals(result)) {
            throw new RuntimeException("Future returned unexpected result: " + result);
        }

        // Test 3: submit Callable that throws checked exception
        Callable<String> throwingCallable = new Callable<String>() {
            public String call() throws Exception {
                throw new Exception("Callable exception");
            }
        };

        Future<String> futureEx = executor.submit(throwingCallable);
        try {
            futureEx.get();
            throw new RuntimeException("Expected ExecutionException from future.get()");
        } catch (ExecutionException ee) {
            Throwable cause = ee.getCause();
            if (cause == null || !"Callable exception".equals(cause.getMessage())) {
                throw new RuntimeException("Unexpected cause in ExecutionException", ee);
            }
        }

        // Test 4: submit Callable that returns null
        Callable<String> nullCallable = new Callable<String>() {
            public String call() {
                return null;
            }
        };

        Future<String> futureNull = executor.submit(nullCallable);
        if (futureNull.get() != null) {
            throw new RuntimeException("Expected null result from Callable");
        }
    }
}
