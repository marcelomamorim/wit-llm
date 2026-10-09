/* @test    @summary Test setMaximumPoolSize method for ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest_P6473b944a741 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest_P6473b944a741 {

    public static void main(String[] args) {
        testSetMaximumPoolSizeIllegalArgument();
        testSetMaximumPoolSizeUnsupportedOperation();
    }

    private static void testSetMaximumPoolSizeIllegalArgument() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setMaximumPoolSize(0);
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize=0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            executor.setMaximumPoolSize(-1);
            throw new AssertionError("Expected IllegalArgumentException for maximumPoolSize=-1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            executor.setMaximumPoolSize(1);
            // Valid, should not throw
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid maximumPoolSize=1", e);
        }
    }

    private static void testSetMaximumPoolSizeUnsupportedOperation() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        // Simulate condition that triggers UnsupportedOperationException
        // Since the actual internal state is private, we assume that if the method is called with a value
        // that causes the internal check to fail, it throws. Here, we test with a value greater than current maximum.
        // Note: In real scenario, this would require setting internal state or mocking, which is not possible here.
        // Therefore, this test is a placeholder to demonstrate the structure.
        try {
            // Using a value that would trigger the exception based on the guard condition
            executor.setMaximumPoolSize(Integer.MAX_VALUE);
            // No exception expected here, so we check if no exception is thrown
        } catch (UnsupportedOperationException e) {
            // Expected in some internal states, but since we cannot simulate internal state, we assume no exception
            throw new AssertionError("Unexpected UnsupportedOperationException", e);
        } catch (Exception e) {
            // Other exceptions are failures
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
