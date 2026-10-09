/*
 * @test
 * @summary Test ThreadPoolExecutor.allowCoreThreadTimeOut behavior
 * @run main ThreadPoolExecutorWitupTest_Pe4f227b6d2a5
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest_Pe4f227b6d2a5 {
    public static void main(String[] args) {
        testAllowCoreThreadTimeOutFalse();
        testAllowCoreThreadTimeOutTrue();
        testAllowCoreThreadTimeOutIllegalArgument();
    }

    static void testAllowCoreThreadTimeOutFalse() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
            1, 1, 1000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));
        tpe.allowCoreThreadTimeOut(false);
        // No exception expected, allowCoreThreadTimeOut remains false
        // No direct getter, so test by setting true then false again
        tpe.allowCoreThreadTimeOut(true);
        tpe.allowCoreThreadTimeOut(false);
        tpe.shutdownNow();
    }

    static void testAllowCoreThreadTimeOutTrue() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
            1, 1, 1000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));
        tpe.allowCoreThreadTimeOut(true);
        // No exception expected, allowCoreThreadTimeOut set to true
        // No direct observable effect here, but no exception means pass
        tpe.shutdownNow();
    }

    static void testAllowCoreThreadTimeOutIllegalArgument() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));
        boolean thrown = false;
        try {
            tpe.allowCoreThreadTimeOut(true);
        } catch (IllegalArgumentException iae) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        }
        tpe.shutdownNow();
    }
}
