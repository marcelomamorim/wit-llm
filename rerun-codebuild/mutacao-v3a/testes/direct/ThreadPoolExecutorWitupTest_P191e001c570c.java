/* @test    @summary Test allowCoreThreadTimeOut method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest_P191e001c570c */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P191e001c570c {

    public static void main(String[] args) {
        testAllowCoreThreadTimeOutValid();
        testAllowCoreThreadTimeOutInvalid();
        testAllowCoreThreadTimeOutStateChange();
    }

    static void testAllowCoreThreadTimeOutValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue<>());
        try {
            executor.allowCoreThreadTimeOut(true);
            assert executor.allowCoreThreadTimeOut == true : "Expected allowCoreThreadTimeOut to be true after setting to true";
        } finally {
            executor.shutdownNow();
        }
    }

    static void testAllowCoreThreadTimeOutInvalid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue<>());
        boolean exceptionThrown = false;
        try {
            executor.allowCoreThreadTimeOut(true);
            // Set keepAliveTime to 0 to test exception
            executor.keepAliveTime = 0; // Accessing package-private for test purposes
            executor.allowCoreThreadTimeOut(true); // Should throw
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        } finally {
            executor.shutdownNow();
        }
        assert exceptionThrown : "Expected IllegalArgumentException when keepAliveTime <= 0 with allowCoreThreadTimeOut=true";
    }

    static void testAllowCoreThreadTimeOutStateChange() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue<>());
        try {
            executor.allowCoreThreadTimeOut(false);
            assert executor.allowCoreThreadTimeOut == false : "Expected allowCoreThreadTimeOut to be false";
            executor.allowCoreThreadTimeOut(true);
            assert executor.allowCoreThreadTimeOut == true : "Expected allowCoreThreadTimeOut to be true after change";
        } finally {
            executor.shutdownNow();
        }
    }
}
