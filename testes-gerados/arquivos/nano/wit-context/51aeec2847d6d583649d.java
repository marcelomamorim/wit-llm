/* @test    @summary Test shutdown method of ThreadPoolExecutor for normal execution and exception path    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdownNormal();
        testShutdownWithLockException();
    }

    private static void testShutdownNormal() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.shutdown();
            boolean isShutdown = executor.isShutdown();
            if (!isShutdown) {
                throw new AssertionError("Executor should be shutdown after shutdown() call");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during shutdown", e);
        }
    }

    private static void testShutdownWithLockException() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10)) {
            @Override
            public void shutdown() {
                // Simulate lock acquisition failure
                throw new UnsupportedOperationException("Simulated exception in shutdown")
            }
        };
        try {
            executor.shutdown();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
