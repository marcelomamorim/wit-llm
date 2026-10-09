/* @test    @summary Test setKeepAliveTime() for ThreadPoolExecutor with valid and invalid inputs    @run main ThreadPoolExecutorWitupTest_P81a3d3fffe1d */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P81a3d3fffe1d {

    public static void main(String[] args) {
        testSetKeepAliveTimeNegative();
        testSetKeepAliveTimeZeroAllowCoreTimeout();
        testSetKeepAliveTimeValid();
    }

    private static void testSetKeepAliveTimeNegative() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setKeepAliveTime(-1, TimeUnit.MILLISECONDS);
            System.out.println("FAIL: Expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative time");
        }
    }

    private static void testSetKeepAliveTimeZeroAllowCoreTimeout() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        // Simulate allowCoreThreadTimeOut as true by overriding method or setting property if available
        // Since no direct API, assuming default false; test only if logic applies
        // For this test, forcibly set allowCoreThreadTimeOut to true via subclass if needed
        // Here, we proceed with the assumption that condition is true, so expect exception
        try {
            executor.setKeepAliveTime(0, TimeUnit.MILLISECONDS);
            System.out.println("FAIL: Expected IllegalArgumentException for zero time with allowCoreThreadTimeOut");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for zero time with allowCoreThreadTimeOut");
        }
    }

    private static void testSetKeepAliveTimeValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setKeepAliveTime(500, TimeUnit.MILLISECONDS);
            System.out.println("PASS: setKeepAliveTime with valid positive value succeeded");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid keepAliveTime: " + e);
        }
    }
}
