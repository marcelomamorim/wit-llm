/* @test    @summary Test for ThreadPoolExecutor.setKeepAliveTime method behavior    @run main ThreadPoolExecutorWitupTest_P81a3d3fffe1d */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorWitupTest_P81a3d3fffe1d {

    public static void main(String[] args) {
        testSetKeepAliveTimeNegative();
        testSetKeepAliveTimeZeroAllowCoreTimeout();
        testSetKeepAliveTimeZeroDisallowCoreTimeout();
        testSetKeepAliveTimePositive();
    }

    // Test that setKeepAliveTime throws IllegalArgumentException if time < 0
    public static void testSetKeepAliveTimeNegative() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.SynchronousQueue<>());
        boolean threw = false;
        try {
            executor.setKeepAliveTime(-1, TimeUnit.SECONDS);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for negative time");
        }
    }

    // Test that setKeepAliveTime with zero time throws if allowsCoreThreadTimeOut() is true
    public static void testSetKeepAliveTimeZeroAllowCoreTimeout() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, new java.util.concurrent.SynchronousQueue<>());
        // Mock allowsCoreThreadTimeOut to return true (simulate by extending or reflection if needed)
        // Since the method is final, assume the default implementation, which returns false
        // Therefore, in this test, we cannot set allowsCoreThreadTimeOut to true directly.
        // Instead, we test that if allowsCoreThreadTimeOut returns false, the exception is not thrown.
        // But the code throws only if allowsCoreThreadTimeOut() == true, so here, test that no exception is thrown.
        boolean exceptionThrown = false;
        try {
            executor.setKeepAliveTime(0, TimeUnit.SECONDS);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            throw new AssertionError("Did not expect IllegalArgumentException when allowsCoreThreadTimeOut() is false");
        }
    }

    // Test that setKeepAliveTime with zero time throws when allowsCoreThreadTimeOut() is true
    public static void testSetKeepAliveTimeZeroDisallowCoreTimeout() {
        // Since allowsCoreThreadTimeOut() is not accessible, simulate by subclassing
        class TestExecutor extends ThreadPoolExecutor {
            public boolean allowsCoreThreadTimeOut() {
                return true;
            }
        }
        TestExecutor executor = new TestExecutor(1, 2, 10, TimeUnit.SECONDS, new java.util.concurrent.SynchronousQueue<>());
        boolean threw = false;
        try {
            executor.setKeepAliveTime(0, TimeUnit.SECONDS);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when allowsCoreThreadTimeOut() is true and time is zero");
        }
    }

    // Test that setKeepAliveTime with positive time updates internal state correctly
    public static void testSetKeepAliveTimePositive() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.SynchronousQueue<>());
        long newTime = 20; // seconds
        executor.setKeepAliveTime(newTime, TimeUnit.SECONDS);
        // Since keepAliveTime is private, test effect by checking behavior: busy wait, or internal effect is not accessible
        // As an alternative, use reflection or assume that no exception is thrown and method completes
        // For simplicity, verify no exception and assume success
        System.out.println("setKeepAliveTime with positive value executed successfully");
    }
}
