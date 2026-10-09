/* @test    @summary Tests for setMaximumPoolSize method in ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest_P100b1d1ab113 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest_P100b1d1ab113 {

    public static void main(String[] args) {
        testSetMaximumPoolSizeValid();
        testSetMaximumPoolSizeInvalidZero();
        testSetMaximumPoolSizeInvalidLessThanCore();
    }

    static void testSetMaximumPoolSizeValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(3);
            if (executor.getMaximumPoolSize() != 3) {
                throw new AssertionError("Expected maximumPoolSize to be 3");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testSetMaximumPoolSizeInvalidZero() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(0);
            throw new AssertionError("Expected IllegalArgumentException for zero maximumPoolSize");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testSetMaximumPoolSizeInvalidLessThanCore() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(2); // less than corePoolSize
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize less than corePoolSize");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
