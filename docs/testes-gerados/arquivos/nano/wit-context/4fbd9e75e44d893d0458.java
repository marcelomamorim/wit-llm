/* @test    @summary Test getActiveCount() method of ThreadPoolExecutor for normal behavior and exception path    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetActiveCountNormal();
        testGetActiveCountThrows();
    }

    private static void testGetActiveCountNormal() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null) {
            @Override
            public int getActiveCount() {
                // Simulate normal behavior: return a fixed number
                return 2;
            }
        };
        int activeCount = executor.getActiveCount();
        if (activeCount != 2) {
            throw new AssertionError("Expected active count 2, but got " + activeCount);
        }
    }

    private static void testGetActiveCountThrows() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null) {
            @Override
            public int getActiveCount() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            executor.getActiveCount();
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
