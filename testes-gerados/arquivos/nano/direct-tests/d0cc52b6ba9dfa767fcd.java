/* @test    @summary Test getLargestPoolSize method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetLargestPoolSizeReturnsCorrectValue();
        testGetLargestPoolSizeWithMultipleCalls();
    }

    private static void testGetLargestPoolSizeReturnsCorrectValue() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        int size = executor.getLargestPoolSize();
        if (size != 0) {
            throw new AssertionError("Expected largestPoolSize to be 0 initially, but was " + size);
        }
    }

    private static void testGetLargestPoolSizeWithMultipleCalls() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        // simulate thread activity
        executor.execute(() -> {});
        int size1 = executor.getLargestPoolSize();
        // simulate more activity
        executor.execute(() -> {});
        int size2 = executor.getLargestPoolSize();
        if (size2 < size1) {
            throw new AssertionError("largestPoolSize should not decrease, but was " + size2 + " after previous size " + size1);
        }
    }
}
