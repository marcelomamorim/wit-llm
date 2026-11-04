/*
 * @test
 * @summary Test ThreadPoolExecutor.setMaximumPoolSize(int) for argument validation and behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testIllegalArgumentException();
        testSetMaximumPoolSizeNormal();
    }

    private static void testIllegalArgumentException() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 4, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        boolean threw = false;
        try {
            executor.setMaximumPoolSize(0); // maximumPoolSize <= 0
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for maximumPoolSize <= 0");
        }

        threw = false;
        try {
            executor.setMaximumPoolSize(1); // maximumPoolSize < corePoolSize (2)
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for maximumPoolSize < corePoolSize");
        }

        executor.shutdownNow();
    }

    private static void testSetMaximumPoolSizeNormal() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 4, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));

        // Set maximumPoolSize to a valid value >= corePoolSize
        executor.setMaximumPoolSize(3);
        if (executor.getMaximumPoolSize() != 3) {
            throw new RuntimeException("maximumPoolSize was not set correctly");
        }

        // Set maximumPoolSize to corePoolSize (boundary)
        executor.setMaximumPoolSize(2);
        if (executor.getMaximumPoolSize() != 2) {
            throw new RuntimeException("maximumPoolSize was not set correctly to corePoolSize");
        }

        executor.shutdownNow();
    }
}
