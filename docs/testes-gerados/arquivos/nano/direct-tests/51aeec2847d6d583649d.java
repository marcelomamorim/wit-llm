/* @test    @summary Test ThreadPoolExecutor.shutdown() method for correct state transition and thread interruption    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdownTransitionsState();
        testShutdownInterruptsIdleWorkers();
    }

    static void testShutdownTransitionsState() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        executor.shutdown();
        boolean isShutdown = executor.isShutdown();
        boolean isTerminated = executor.isTerminated();
        if (!isShutdown) {
            throw new AssertionError("Executor should be in shutdown state after shutdown() call");
        }
        if (isTerminated) {
            throw new AssertionError("Executor should not be terminated immediately after shutdown() call");
        }
    }

    static void testShutdownInterruptsIdleWorkers() {
        final AtomicBoolean interrupted = new AtomicBoolean(false);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10)) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                // No action needed
            }
        };
        // Submit a dummy task to ensure thread is created
        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrupted.set(true);
            }
        });
        // Wait briefly to ensure thread starts
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }
        executor.shutdown();
        // Wait for thread to acknowledge interruption
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // ignore
        }
        if (!interrupted.get()) {
            throw new AssertionError("Worker thread was not interrupted during shutdown");
        }
        if (!executor.isShutdown()) {
            throw new AssertionError("Executor should be in shutdown state after shutdown() call");
        }
    }
}
