/*
 * @test
 * @summary Test ThreadPoolExecutor.setMaximumPoolSize behavior
 * @run main ThreadPoolExecutorWitupTest_P660f7b48de18
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest_P660f7b48de18 {
    public static void main(String[] args) {
        testSetMaximumPoolSizeValid();
        testSetMaximumPoolSizeThrows();
    }

    static void testSetMaximumPoolSizeValid() {
        // Create ThreadPoolExecutor with corePoolSize=2, maximumPoolSize=4
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 10L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10));

        // Set maximumPoolSize to a valid value greater than corePoolSize
        tpe.setMaximumPoolSize(3);
        if (tpe.getMaximumPoolSize() != 3) {
            throw new AssertionError("Expected maximumPoolSize to be 3 but was " + tpe.getMaximumPoolSize());
        }

        // Set maximumPoolSize equal to corePoolSize
        tpe.setMaximumPoolSize(2);
        if (tpe.getMaximumPoolSize() != 2) {
            throw new AssertionError("Expected maximumPoolSize to be 2 but was " + tpe.getMaximumPoolSize());
        }

        // Set maximumPoolSize greater than current maximum
        tpe.setMaximumPoolSize(5);
        if (tpe.getMaximumPoolSize() != 5) {
            throw new AssertionError("Expected maximumPoolSize to be 5 but was " + tpe.getMaximumPoolSize());
        }

        tpe.shutdownNow();
    }

    static void testSetMaximumPoolSizeThrows() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 10L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10));

        // maximumPoolSize <= 0 should throw IllegalArgumentException
        try {
            tpe.setMaximumPoolSize(0);
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize=0");
        } catch (IllegalArgumentException expected) {
        }

        try {
            tpe.setMaximumPoolSize(-1);
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize=-1");
        } catch (IllegalArgumentException expected) {
        }

        // maximumPoolSize < corePoolSize should throw IllegalArgumentException
        try {
            tpe.setMaximumPoolSize(1); // corePoolSize is 2
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize < corePoolSize");
        } catch (IllegalArgumentException expected) {
        }

        tpe.shutdownNow();
    }
}
