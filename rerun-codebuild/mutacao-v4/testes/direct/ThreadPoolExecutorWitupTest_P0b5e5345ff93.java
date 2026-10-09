/* @test
   @summary Tests setMaximumPoolSize method of ThreadPoolExecutor.
   @run main ThreadPoolExecutorWitupTest_P0b5e5345ff93
 */
import java.util.concurrent.ThreadPoolExecutor;
public class ThreadPoolExecutorWitupTest_P0b5e5345ff93 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        // Test setting maximumPoolSize to a valid value greater than corePoolSize
        executor.setMaximumPoolSize(5);
        if (executor.getMaximumPoolSize() != 5) {
            throw new AssertionError("Failed to set maximumPoolSize to 5");
        }
        // Test setting maximumPoolSize to a value less than corePoolSize should throw IllegalArgumentException
        try {
            executor.setCorePoolSize(4);
            executor.setMaximumPoolSize(3);
            throw new AssertionError("Expected IllegalArgumentException when setting maximumPoolSize less than corePoolSize");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Test setting maximumPoolSize to a negative value should throw IllegalArgumentException
        try {
            executor.setMaximumPoolSize(-1);
            throw new AssertionError("Expected IllegalArgumentException when setting maximumPoolSize to negative value");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Test setting maximumPoolSize to 0 should throw IllegalArgumentException
        try {
            executor.setMaximumPoolSize(0);
            throw new AssertionError("Expected IllegalArgumentException when setting maximumPoolSize to zero");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Test that setting maximumPoolSize to a value less than current worker count triggers interruptIdleWorkers (behavior not directly observable here)
        // Since interruptIdleWorkers is protected and not observable, we test only that the value is set correctly after valid set
        executor.setMaximumPoolSize(10);
        if (executor.getMaximumPoolSize() != 10) {
            throw new AssertionError("Failed to set maximumPoolSize to 10");
        }
    }
}
