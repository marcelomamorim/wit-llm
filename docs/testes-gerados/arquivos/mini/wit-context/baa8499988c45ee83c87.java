/*
 * @test
 * @summary Test AbstractExecutorService.submit(Runnable) for null input and normal execution
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceWitupTest {

    static class DummyExecutor extends AbstractExecutorService {

        private volatile boolean shutdown = false;

        public void execute(Runnable command) {
            // Run the command synchronously for test simplicity
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
            return true;
        }

        // Override newTaskFor to use default FutureTask
        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
            return new FutureTask<T>(runnable, value);
        }
    }

    public static void main(String[] args) throws Exception {
        DummyExecutor executor = new DummyExecutor();

        // Test 1: submit(null) should throw NullPointerException
        try {
            executor.submit(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test 2: submit a simple Runnable and verify returned Future
        Runnable task = new Runnable() {
            boolean ran = false;
            public void run() {
                ran = true;
            }
        };

        // Use a wrapper Runnable to observe execution
        class FlagRunnable implements Runnable {
            boolean ran = false;
            public void run() { ran = true; }
        }

        FlagRunnable flagRunnable = new FlagRunnable();
        Future<?> future = executor.submit(flagRunnable);

        if (future == null) {
            throw new RuntimeException("submit returned null Future");
        }

        // Since execute runs synchronously, flagRunnable.ran should be true
        if (!flagRunnable.ran) {
            throw new RuntimeException("Runnable was not executed");
        }

        // Future should be done
        if (!future.isDone()) {
            throw new RuntimeException("Future is not done after execution");
        }

        // get() should return null for Runnable submitted
        Object result = future.get();
        if (result != null) {
            throw new RuntimeException("Future.get() returned non-null for Runnable task");
        }
    }
}
