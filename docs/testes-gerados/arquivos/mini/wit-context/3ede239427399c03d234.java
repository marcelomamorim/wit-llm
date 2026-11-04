/*
 * @test
 * @summary Test unconfigurableScheduledExecutorService method for null input and normal wrapping
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNullExecutorThrows();
        testWrapsExecutor();
    }

    static void testNullExecutorThrows() {
        try {
            Executors.unconfigurableScheduledExecutorService(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testWrapsExecutor() throws Exception {
        ScheduledExecutorService scheduled = new ScheduledExecutorService() {
            public void shutdown() {}
            public java.util.List<Runnable> shutdownNow() { return null; }
            public boolean isShutdown() { return false; }
            public boolean isTerminated() { return false; }
            public boolean awaitTermination(long timeout, TimeUnit unit) { return true; }
            public <V> java.util.concurrent.Future<V> submit(java.util.concurrent.Callable<V> task) { return null; }
            public <V> java.util.concurrent.Future<V> submit(Runnable task, V result) { return null; }
            public java.util.concurrent.Future<?> submit(Runnable task) { return null; }
            public <V> java.util.List<java.util.concurrent.Future<V>> invokeAll(java.util.Collection<? extends java.util.concurrent.Callable<V>> tasks) { return null; }
            public <V> java.util.List<java.util.concurrent.Future<V>> invokeAll(java.util.Collection<? extends java.util.concurrent.Callable<V>> tasks, long timeout, TimeUnit unit) { return null; }
            public <V> V invokeAny(java.util.Collection<? extends java.util.concurrent.Callable<V>> tasks) { return null; }
            public <V> V invokeAny(java.util.Collection<? extends java.util.concurrent.Callable<V>> tasks, long timeout, TimeUnit unit) { return null; }
            public void execute(Runnable command) {}
            public java.util.concurrent.ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) { return null; }
            public <V> java.util.concurrent.ScheduledFuture<V> schedule(java.util.concurrent.Callable<V> callable, long delay, TimeUnit unit) { return null; }
            public java.util.concurrent.ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) { return null; }
            public java.util.concurrent.ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) { return null; }
        };

        ScheduledExecutorService wrapped = Executors.unconfigurableScheduledExecutorService(scheduled);
        if (wrapped == null) {
            throw new Error("Wrapped ScheduledExecutorService is null");
        }
        // Check that wrapped is not the same instance
        if (wrapped == scheduled) {
            throw new Error("Wrapped ScheduledExecutorService should not be the same instance as input");
        }
        // Check that wrapped delegates execute call
        final boolean[] executed = new boolean[1];
        Runnable r = new Runnable() {
            public void run() {
                executed[0] = true;
            }
        };
        wrapped.execute(r);
        // Since execute is asynchronous, wait briefly
        Thread.sleep(50);
        if (!executed[0]) {
            throw new Error("Wrapped ScheduledExecutorService did not execute runnable");
        }
    }
}
