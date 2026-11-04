/*
 * @test
 * @summary Test java.lang.Object.wait(long, int) method behavior and exceptions
 * @run main ObjectWitupTest
 */

public class ObjectWitupTest {
    public static void main(String[] args) throws Exception {
        testNegativeTimeoutMillis();
        testNegativeNanos();
        testNanosTooLarge();
        testWaitWithNanosIncrement();
        testWaitWithZeroNanos();
    }

    private static void testNegativeTimeoutMillis() {
        Object obj = new Object();
        try {
            synchronized (obj) {
                obj.wait(-1L, 0);
                throw new RuntimeException("Expected IllegalArgumentException for negative timeoutMillis");
            }
        } catch (IllegalArgumentException e) {
            if (!"timeoutMillis value is negative".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected InterruptedException", e);
        }
    }

    private static void testNegativeNanos() {
        Object obj = new Object();
        try {
            synchronized (obj) {
                obj.wait(0L, -1);
                throw new RuntimeException("Expected IllegalArgumentException for negative nanos");
            }
        } catch (IllegalArgumentException e) {
            if (!"nanosecond timeout value out of range".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected InterruptedException", e);
        }
    }

    private static void testNanosTooLarge() {
        Object obj = new Object();
        try {
            synchronized (obj) {
                obj.wait(0L, 1000000);
                throw new RuntimeException("Expected IllegalArgumentException for nanos > 999999");
            }
        } catch (IllegalArgumentException e) {
            if (!"nanosecond timeout value out of range".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected InterruptedException", e);
        }
    }

    private static void testWaitWithNanosIncrement() throws InterruptedException {
        final Object obj = new Object();
        final long start = System.currentTimeMillis();
        synchronized (obj) {
            // nanos > 0 should increment timeoutMillis by 1
            obj.wait(10L, 1);
        }
        final long elapsed = System.currentTimeMillis() - start;
        if (elapsed < 10) {
            throw new RuntimeException("wait did not wait at least the expected time with nanos > 0");
        }
    }

    private static void testWaitWithZeroNanos() throws InterruptedException {
        final Object obj = new Object();
        final long start = System.currentTimeMillis();
        synchronized (obj) {
            // nanos == 0 should not increment timeoutMillis
            obj.wait(10L, 0);
        }
        final long elapsed = System.currentTimeMillis() - start;
        if (elapsed < 10) {
            throw new RuntimeException("wait did not wait at least the expected time with nanos == 0");
        }
    }
}
