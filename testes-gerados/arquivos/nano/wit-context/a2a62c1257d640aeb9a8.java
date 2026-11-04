/*
 * @test
 * @summary Tests for java.util.concurrent.Executors.newCachedThreadPool()
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testValidInvocation();
        testNegativeCorePoolSize();
        testNegativeKeepAliveTime();
    }

    public static void testValidInvocation() {
        ExecutorService executor = null;
        try {
            executor = Executors.newCachedThreadPool();
            if (!(executor instanceof ThreadPoolExecutor)) {
                throw new AssertionError("Expected instance of ThreadPoolExecutor");
            }
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;
            // Verify corePoolSize is 0
            if (tpe.getCorePoolSize() != 0) {
                throw new AssertionError("corePoolSize should be 0");
            }
            // Verify maximumPoolSize is Integer.MAX_VALUE
            if (tpe.getMaximumPoolSize() != Integer.MAX_VALUE) {
                throw new AssertionError("maximumPoolSize should be Integer.MAX_VALUE");
            }
            // Verify keepAliveTime is 60L
            if (tpe.getKeepAliveTime(TimeUnit.SECONDS) != 60L) {
                throw new AssertionError("keepAliveTime should be 60L");
            }
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    public static void testNegativeCorePoolSize() {
        try {
            ThreadPoolExecutor tpe = new ThreadPoolExecutor(-1, 10, 60L, TimeUnit.SECONDS, new java.util.concurrent.SynchronousQueue<Runnable>());
            throw new AssertionError("Expected IllegalArgumentException for negative corePoolSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    public static void testNegativeKeepAliveTime() {
        try {
            ThreadPoolExecutor tpe = new ThreadPoolExecutor(0, 10, -1L, TimeUnit.SECONDS, new java.util.concurrent.SynchronousQueue<Runnable>());
            throw new AssertionError("Expected IllegalArgumentException for negative keepAliveTime");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
