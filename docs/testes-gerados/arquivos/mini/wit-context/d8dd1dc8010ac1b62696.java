/*
 * @test
 * @summary Test java.time.Duration.plusMillis(long) for normal and overflow cases
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusMillisNormal();
        testPlusMillisOverflow();
    }

    private static void testPlusMillisNormal() {
        Duration d = Duration.ofSeconds(10, 500_000_000); // 10.5 seconds
        Duration result = d.plusMillis(1500); // add 1.5 seconds
        // Expected: 12 seconds (10.5 + 1.5)
        if (result.getSeconds() != 12 || result.getNano() != 0) {
            throw new AssertionError("plusMillis normal addition failed: " + result);
        }

        // Adding zero millis should return equal duration
        Duration same = d.plusMillis(0);
        if (!same.equals(d)) {
            throw new AssertionError("plusMillis zero addition failed: " + same);
        }

        // Adding negative millis
        Duration neg = d.plusMillis(-500);
        if (neg.getSeconds() != 10 || neg.getNano() != 0) {
            throw new AssertionError("plusMillis negative addition failed: " + neg);
        }
    }

    private static void testPlusMillisOverflow() {
        // Test that adding millis causing overflow throws ArithmeticException
        // Use large values near Long.MAX_VALUE seconds
        Duration max = Duration.ofSeconds(Long.MAX_VALUE, 999_999_999);
        try {
            max.plusMillis(1);
            throw new AssertionError("Expected ArithmeticException not thrown on overflow");
        } catch (ArithmeticException expected) {
            // expected
        }

        // Also test negative overflow
        Duration min = Duration.ofSeconds(Long.MIN_VALUE, 0);
        try {
            min.plusMillis(-1);
            throw new AssertionError("Expected ArithmeticException not thrown on negative overflow");
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
