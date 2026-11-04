/*
 * @test
 * @summary Test AbstractExecutorService.submit(Runnable) method behavior
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
            // Run the command synchronously in the calling thread
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
        SimpleExecutor executor = new SimpleExecutor();

        // Test 1: submit with null task should throw NullPointerException
        try {
            executor.submit(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: submit with a simple Runnable that sets a flag
        final boolean[] ran = {false};
        Runnable task = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };

        Future<?> future = executor.submit(task);

        if (future == null) {
            throw new RuntimeException("submit returned null Future");
        }

        // The task should have run synchronously
        if (!ran[0]) {
            throw new RuntimeException("Runnable task was not executed");
        }

        // The Future should be done
        if (!future.isDone()) {
            throw new RuntimeException("Future is not done after execution");
        }

        // The Future get() should return null (because newTaskFor uses null as result)
        Object result = future.get();
        if (result != null) {
            throw new RuntimeException("Future.get() returned non-null result");
        }

        // The Future cancel should return false because task already completed
        if (future.cancel(true)) {
            throw new RuntimeException("Future.cancel(true) returned true after completion");
        }

        // The Future isCancelled should be false
        if (future.isCancelled()) {
            throw new RuntimeException("Future.isCancelled() returned true after completion");
        }

        System.out.println("All tests passed.");
    }
}
