/* @test    @summary Test setMaximumPoolSize method of ThreadPoolExecutor for valid and invalid inputs    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSetMaximumPoolSizeValid();
        testSetMaximumPoolSizeInvalid();
    }

    static void testSetMaximumPoolSizeValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        int newMaxSize = 3;
        executor.setMaximumPoolSize(newMaxSize);
        assert executor.getMaximumPoolSize() == newMaxSize : "Expected maximumPoolSize to be " + newMaxSize;
    }

    static void testSetMaximumPoolSizeInvalid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(0);
            assert false : "Expected IllegalArgumentException for maximumPoolSize=0";
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            executor.setMaximumPoolSize(-1);
            assert false : "Expected IllegalArgumentException for maximumPoolSize=-1";
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            executor.setMaximumPoolSize(1);
            executor.setCorePoolSize(2);
            // Now, corePoolSize > maximumPoolSize, should throw
            executor.setMaximumPoolSize(1);
            assert false : "Expected IllegalArgumentException when maximumPoolSize < corePoolSize";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
