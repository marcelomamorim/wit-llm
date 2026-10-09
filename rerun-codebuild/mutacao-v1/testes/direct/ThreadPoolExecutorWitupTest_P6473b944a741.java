/* @test    @summary Test setMaximumPoolSize method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest_P6473b944a741 */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P6473b944a741 {

    public static void main(String[] args) {
        testSetMaximumPoolSizeValid();
        testSetMaximumPoolSizeInvalid();
        testSetMaximumPoolSizeBelowCorePoolSize();
    }

    static void testSetMaximumPoolSizeValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(5);
            if (executor.getMaximumPoolSize() != 5) {
                throw new AssertionError("Expected maximumPoolSize to be 5, but was " + executor.getMaximumPoolSize());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testSetMaximumPoolSizeValid", e);
        }
    }

    static void testSetMaximumPoolSizeInvalid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        boolean thrown = false;
        try {
            executor.setMaximumPoolSize(0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize=0");
        }
        thrown = false;
        try {
            executor.setMaximumPoolSize(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize=-1");
        }
    }

    static void testSetMaximumPoolSizeBelowCorePoolSize() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(2);
            if (executor.getMaximumPoolSize() != 2) {
                throw new AssertionError("Expected maximumPoolSize to be 2, but was " + executor.getMaximumPoolSize());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testSetMaximumPoolSizeBelowCorePoolSize", e);
        }
    }
}
