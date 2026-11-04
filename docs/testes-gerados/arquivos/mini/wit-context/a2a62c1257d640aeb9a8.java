/*
 * @test
 * @summary Test newCachedThreadPool method of Executors for correct behavior and exception safety
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.SynchronousQueue;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNewCachedThreadPoolReturnsThreadPoolExecutor();
        testNewCachedThreadPoolDoesNotThrow();
    }

    private static void testNewCachedThreadPoolReturnsThreadPoolExecutor() {
        ExecutorService es = java.util.concurrent.Executors.newCachedThreadPool();
        if (!(es instanceof ThreadPoolExecutor)) {
            throw new AssertionError("newCachedThreadPool() should return a ThreadPoolExecutor instance");
        }
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;

        // Check corePoolSize == 0
        if (tpe.getCorePoolSize() != 0) {
            throw new AssertionError("corePoolSize should be 0");
        }

        // Check maximumPoolSize == Integer.MAX_VALUE
        if (tpe.getMaximumPoolSize() != Integer.MAX_VALUE) {
            throw new AssertionError("maximumPoolSize should be Integer.MAX_VALUE");
        }

        // Check keepAliveTime == 60 seconds
        long keepAlive = tpe.getKeepAliveTime(TimeUnit.SECONDS);
        if (keepAlive != 60L) {
            throw new AssertionError("keepAliveTime should be 60 seconds");
        }

        // Check workQueue is a SynchronousQueue
        if (!(tpe.getQueue() instanceof SynchronousQueue)) {
            throw new AssertionError("workQueue should be a SynchronousQueue");
        }

        es.shutdownNow();
    }

    private static void testNewCachedThreadPoolDoesNotThrow() {
        // The method newCachedThreadPool() does not take parameters and should not throw
        // IllegalArgumentException or any other exception under normal conditions.
        try {
            ExecutorService es = java.util.concurrent.Executors.newCachedThreadPool();
            es.shutdownNow();
        } catch (Exception e) {
            throw new AssertionError("newCachedThreadPool() should not throw any exception", e);
        }
    }
}
