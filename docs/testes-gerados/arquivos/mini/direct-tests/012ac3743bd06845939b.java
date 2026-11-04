/*
 * @test
 * @summary Test ExecutorCompletionService constructor behavior
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Future;

public class ExecutorCompletionServiceWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithNullExecutor();
        testConstructorWithSimpleExecutor();
        testConstructorWithAbstractExecutorService();
    }

    private static void testConstructorWithNullExecutor() {
        try {
            new ExecutorCompletionService<>(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorWithSimpleExecutor() {
        Executor simpleExecutor = new Executor() {
            public void execute(Runnable r) {
                // no-op
            }
        };
        ExecutorCompletionService<Object> ecs = new ExecutorCompletionService<>(simpleExecutor);
        if (ecs == null) throw new RuntimeException("Constructor returned null");

        // Check that aes field is null since executor is not AbstractExecutorService
        // We cannot access aes directly, so we test observable behavior:
        // Submit a task and check that completionQueue is not null by polling
        try {
            ecs.submit(() -> "test");
            Future<Object> f = ecs.take();
            Object result = f.get();
            if (!"test".equals(result)) {
                throw new RuntimeException("Unexpected result from submitted task");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testConstructorWithAbstractExecutorService() {
        AbstractExecutorService aes = new AbstractExecutorService() {
            private volatile boolean shutdown = false;
            public void shutdown() { shutdown = true; }
            public java.util.List<Runnable> shutdownNow() { shutdown = true; return java.util.Collections.emptyList(); }
            public boolean isShutdown() { return shutdown; }
            public boolean isTerminated() { return shutdown; }
            public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) { return shutdown; }
            public void execute(Runnable command) { new Thread(command).start(); }
        };

        ExecutorCompletionService<Object> ecs = new ExecutorCompletionService<>(aes);
        if (ecs == null) throw new RuntimeException("Constructor returned null");

        // Submit a task and check that completionQueue is not null by polling
        try {
            ecs.submit(() -> "test2");
            Future<Object> f = ecs.take();
            Object result = f.get();
            if (!"test2".equals(result)) {
                throw new RuntimeException("Unexpected result from submitted task");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
