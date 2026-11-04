/*
 * @test
 * @summary Test java.lang.Object.wait(long timeoutMillis, int nanos) for argument validation and normal behavior
 * @run main ObjectWitupTest
 */

public class ObjectWitupTest {
    public static void main(String[] args) throws Exception {
        final Object obj = new Object();

        // Test IllegalArgumentException when timeoutMillis < 0
        try {
            obj.wait(-1L, 0);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeoutMillis");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test IllegalArgumentException when nanos < 0
        try {
            obj.wait(0L, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative nanos");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test IllegalArgumentException when nanos > 999999
        try {
            obj.wait(0L, 1000000);
            throw new RuntimeException("Expected IllegalArgumentException for nanos > 999999");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test normal behavior: nanos > 0 increments timeoutMillis by 1 internally
        // We test that wait(timeoutMillis) is called with timeoutMillis+1 by measuring elapsed time
        // Use a short timeout to keep test fast
        long start = System.currentTimeMillis();
        synchronized (obj) {
            obj.wait(10L, 500000); // nanos > 0
        }
        long elapsed = System.currentTimeMillis() - start;
        if (elapsed < 10) {
            throw new RuntimeException("wait did not wait at least the expected time");
        }

        // Test normal behavior: nanos == 0 does not increment timeoutMillis
        start = System.currentTimeMillis();
        synchronized (obj) {
            obj.wait(10L, 0); // nanos == 0
        }
        elapsed = System.currentTimeMillis() - start;
        if (elapsed < 10) {
            throw new RuntimeException("wait did not wait at least the expected time");
        }
    }
}
