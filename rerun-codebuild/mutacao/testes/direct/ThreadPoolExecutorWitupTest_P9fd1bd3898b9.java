/* @test    @summary Test allowCoreThreadTimeOut method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest_P9fd1bd3898b9 */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P9fd1bd3898b9 {

    public static void main(String[] args) {
        testAllowCoreThreadTimeOut();
    }

    public static void testAllowCoreThreadTimeOut() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        // Test setting allowCoreThreadTimeOut to true
        executor.allowCoreThreadTimeOut(true);
        // Verify that the value is set (no exception thrown)
        // Test setting allowCoreThreadTimeOut to false
        executor.allowCoreThreadTimeOut(false);
        // Verify that the value is set (no exception thrown)
        // Test invalid case: keepAliveTime <= 0 when enabling allowCoreThreadTimeOut
        try {
            ThreadPoolExecutor executor2 = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
            executor2.allowCoreThreadTimeOut(true);
            // Should throw IllegalArgumentException
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
