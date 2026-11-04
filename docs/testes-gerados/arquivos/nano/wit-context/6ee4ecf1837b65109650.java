/* @test    @summary Test for ThreadPoolExecutor.allowCoreThreadTimeOut method handling exception paths    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testAllowCoreThreadTimeOutThrowsExceptionOnInvalidInput();
    }

    private static void testAllowCoreThreadTimeOutThrowsExceptionOnInvalidInput() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        boolean[] testValues = {true, false};
        for (boolean value : testValues) {
            try {
                // Set keepAliveTime to zero to trigger exception when value is true
                executor.setKeepAliveTime(0, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (value) {
                    executor.allowCoreThreadTimeOut(value);
                    // If no exception, test fails
                    System.err.println("Expected IllegalArgumentException not thrown for value: " + value);
                }
            } catch (IllegalArgumentException e) {
                // Verify exception message contains expected text
                if (!e.getMessage().contains("Core threads must have nonzero keep alive times")) {
                    System.err.println("Unexpected exception message: " + e.getMessage());
                } else {
                    System.out.println("Caught expected IllegalArgumentException for value: " + value);
                }
            } catch (Exception e) {
                System.err.println("Unexpected exception type: " + e);
            }
        }
    }
}
