/* @test    @summary Test allowCoreThreadTimeOut method for IllegalArgumentException on invalid keepAliveTime    @run main ThreadPoolExecutorWitupTest_P1bfc10877238 */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P1bfc10877238 {

    public static void main(String[] args) {
        testAllowCoreThreadTimeOutThrowsException();
    }

    private static void testAllowCoreThreadTimeOutThrowsException() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        boolean exceptionThrown = false;
        try {
            // Trigger condition: value = true, keepAliveTime <= 0
            executor.allowCoreThreadTimeOut(true);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown when allowCoreThreadTimeOut(true) with keepAliveTime <= 0");
        }
        // Now test with value = false, should not throw
        try {
            executor.allowCoreThreadTimeOut(false);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when calling allowCoreThreadTimeOut(false)", e);
        }
        // Test with value = true and keepAliveTime > 0, should not throw
        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(1, 1, 10L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor2.allowCoreThreadTimeOut(true);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when calling allowCoreThreadTimeOut(true) with keepAliveTime > 0", e);
        }
    }
}
