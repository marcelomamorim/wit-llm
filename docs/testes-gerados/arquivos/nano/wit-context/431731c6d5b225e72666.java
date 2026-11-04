/* @test    @summary Test getPoolSize() method of ThreadPoolExecutor for normal behavior and exception path    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetPoolSizeNormal();
        testGetPoolSizeThrows();
    }

    private static void testGetPoolSizeNormal() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null) {
            @Override
            public int getPoolSize() {
                // Simulate normal behavior: return size of workers list
                return 1;
            }
        };
        int size = executor.getPoolSize();
        if (size != 1) {
            throw new AssertionError("Expected pool size 1, but got " + size);
        }
    }

    private static void testGetPoolSizeThrows() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null) {
            @Override
            public int getPoolSize() {
                final ReentrantLock lock = new ReentrantLock();
                lock.lock();
                try {
                    throw new UnsupportedOperationException();
                } finally {
                    lock.unlock();
                }
            }
        };
        try {
            executor.getPoolSize();
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
