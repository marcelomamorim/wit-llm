/*
 * @test
 * @summary Test AbstractExecutorService.submit(Runnable, T) method
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AbstractExecutorServiceWitupTest {

    static class SimpleExecutor extends AbstractExecutorService {
        private volatile boolean shutdown = false;

        public void execute(Runnable command) {
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

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return shutdown;
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleExecutor exec = new SimpleExecutor();

        // Test 1: submit with non-null Runnable and non-null result
        Runnable r1 = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        String result1 = "test1";
        Future<String> f1 = exec.submit(r1, result1);
        if (!f1.isDone()) {
            throw new RuntimeException("Future should be done after execute");
        }
        if (!result1.equals(f1.get())) {
            throw new RuntimeException("Future result mismatch");
        }

        // Test 2: submit with non-null Runnable and null result
        Runnable r2 = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        Future<Object> f2 = exec.submit(r2, null);
        if (!f2.isDone()) {
            throw new RuntimeException("Future should be done after execute");
        }
        if (f2.get() != null) {
            throw new RuntimeException("Future result should be null");
        }

        // Test 3: submit with Runnable that throws RuntimeException
        Runnable r3 = new Runnable() {
            public void run() {
                throw new RuntimeException("Intentional");
            }
        };
        Future<Object> f3 = exec.submit(r3, null);
        try {
            f3.get();
            throw new RuntimeException("Expected ExecutionException");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof RuntimeException) || !"Intentional".equals(cause.getMessage())) {
                throw new RuntimeException("Unexpected cause", e);
            }
        }

        // Test 4: submit with null Runnable should throw NullPointerException
        boolean npeThrown = false;
        try {
            exec.submit(null, "x");
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null Runnable");
        }

        System.out.println("All tests passed.");
    }
}
