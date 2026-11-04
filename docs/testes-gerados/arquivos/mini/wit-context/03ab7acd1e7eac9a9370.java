/*
 * @test
 * @summary Test AbstractExecutorService.submit(Runnable, T) for null task and normal execution
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AbstractExecutorServiceWitupTest {

    static class DummyExecutor extends AbstractExecutorService {
        private volatile boolean shutdown = false;

        public void execute(Runnable command) {
            // Run synchronously in current thread
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
        DummyExecutor executor = new DummyExecutor();

        // Test null task throws NullPointerException
        try {
            executor.submit(null, "ignored");
            throw new RuntimeException("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test normal execution with Runnable and result
        final boolean[] ran = new boolean[1];
        Runnable task = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };

        Future<String> future = executor.submit(task, "result");

        // The task should have run synchronously
        if (!ran[0]) {
            throw new RuntimeException("Runnable task was not executed");
        }

        // The future should be done
        if (!future.isDone()) {
            throw new RuntimeException("Future should be done after execution");
        }

        // The future get should return the given result
        String res = future.get();
        if (!"result".equals(res)) {
            throw new RuntimeException("Future.get() returned unexpected result: " + res);
        }

        // Cancel should not affect completed task
        boolean cancelResult = future.cancel(true);
        if (cancelResult) {
            throw new RuntimeException("Cancel should return false for completed task");
        }

        // isCancelled should be false
        if (future.isCancelled()) {
            throw new RuntimeException("Future should not be cancelled");
        }
    }
}
