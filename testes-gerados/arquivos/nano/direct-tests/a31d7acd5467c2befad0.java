/* @test    @summary Test setKeepAliveTime method of ThreadPoolExecutor for valid and invalid inputs    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSetKeepAliveTimeWithNegative();
        testSetKeepAliveTimeWithZeroAndTimeout();
        testSetKeepAliveTimeWithPositive();
    }

    static void testSetKeepAliveTimeWithNegative() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        try {
            executor.setKeepAliveTime(-1, TimeUnit.SECONDS);
            // Expecting IllegalArgumentException
            assert false : "Expected IllegalArgumentException for negative time";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testSetKeepAliveTimeWithZeroAndTimeout() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        try {
            executor.setKeepAliveTime(0, TimeUnit.SECONDS);
            // No exception expected here, but if allowsCoreThreadTimeOut() returns true, exception is thrown
            // Since allowsCoreThreadTimeOut() is not mocked, assume false and no exception
        } catch (IllegalArgumentException e) {
            // If exception occurs, test fails
            assert false : "Did not expect IllegalArgumentException for zero when allowsCoreThreadTimeOut() is false";
        }
    }

    static void testSetKeepAliveTimeWithPositive() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        try {
            executor.setKeepAliveTime(5, TimeUnit.SECONDS);
            // No exception expected, verify internal state if accessible (not in this test)
        } catch (IllegalArgumentException e) {
            assert false : "Did not expect IllegalArgumentException for positive time";
        }
    }
}
