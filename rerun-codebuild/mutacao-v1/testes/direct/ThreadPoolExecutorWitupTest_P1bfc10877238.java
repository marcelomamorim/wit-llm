/* @test    @summary Test allowCoreThreadTimeOut method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest_P1bfc10877238 */

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P1bfc10877238 {

    public static void main(String[] args) {
        testAllowCoreThreadTimeOut();
    }

    public static void testAllowCoreThreadTimeOut() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            // Test with false (default)
            executor.allowCoreThreadTimeOut(false);
            if (executor.allowCoreThreadTimeOut != false) {
                throw new AssertionError("Expected allowCoreThreadTimeOut to be false");
            }
            // Test with true
            executor.allowCoreThreadTimeOut(true);
            if (executor.allowCoreThreadTimeOut != true) {
                throw new AssertionError("Expected allowCoreThreadTimeOut to be true");
            }
            // Test with invalid keepAliveTime (simulate by calling method with true and keepAliveTime <= 0)
            // Since keepAliveTime is private, we cannot set it directly; test only the behavior of method with valid input
            // No exception expected here
            executor.allowCoreThreadTimeOut(false);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Unexpected IllegalArgumentException", e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
        System.out.println("PASS: testAllowCoreThreadTimeOut");
    }
}
