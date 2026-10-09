/*
 * @test
 * @summary Test ThreadPoolExecutor.allowCoreThreadTimeOut(boolean) for argument validation and state change
 * @run main ThreadPoolExecutorWitupTest_Pe4f227b6d2a5
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest_Pe4f227b6d2a5 {
    public static void main(String[] args) {
        // Create a ThreadPoolExecutor with keepAliveTime = 0
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));

        // Test that allowCoreThreadTimeOut(true) with keepAliveTime <= 0 throws IllegalArgumentException
        boolean caught = false;
        try {
            executor.allowCoreThreadTimeOut(true);
        } catch (IllegalArgumentException iae) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown when allowCoreThreadTimeOut(true) with keepAliveTime=0");
        }

        // Test that allowCoreThreadTimeOut(false) does not throw
        executor.allowCoreThreadTimeOut(false);

        // Test that allowCoreThreadTimeOut(true) with positive keepAliveTime does not throw
        executor.setKeepAliveTime(10L, TimeUnit.MILLISECONDS);
        executor.allowCoreThreadTimeOut(true);

        // Test that calling allowCoreThreadTimeOut(true) again does not throw and does not cause error
        executor.allowCoreThreadTimeOut(true);

        // Test that calling allowCoreThreadTimeOut(false) again does not throw
        executor.allowCoreThreadTimeOut(false);
    }
}
