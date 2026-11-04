/*
 * @test
 * @summary Test java.time.Duration.plusNanos(long) for normal and overflow cases
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusNanosNormal();
        testPlusNanosOverflow();
    }

    private static void testPlusNanosNormal() {
        Duration d = Duration.ofSeconds(1, 500);
        Duration result = d.plusNanos(500);
        // Expect 1 second + 1000 nanos
        if (result.getSeconds() != 1 || result.getNano() != 1000) {
            throw new AssertionError("plusNanos normal addition failed: " + result);
        }
        // Adding zero nanos returns equal duration
        Duration same = d.plusNanos(0);
        if (!same.equals(d)) {
            throw new AssertionError("plusNanos with zero nanos should return equal duration");
        }
    }

    private static void testPlusNanosOverflow() {
        // Test that adding nanos causing seconds overflow throws ArithmeticException
        // Use max long seconds to cause overflow
        Duration max = Duration.ofSeconds(Long.MAX_VALUE, 0);
        try {
            max.plusNanos(1);
            throw new AssertionError("Expected ArithmeticException not thrown on overflow");
        } catch (ArithmeticException expected) {
            // Expected
        }

        // Also test negative overflow
        Duration min = Duration.ofSeconds(Long.MIN_VALUE, 0);
        try {
            min.plusNanos(-1);
            throw new AssertionError("Expected ArithmeticException not thrown on negative overflow");
        } catch (ArithmeticException expected) {
            // Expected
        }
    }
}
