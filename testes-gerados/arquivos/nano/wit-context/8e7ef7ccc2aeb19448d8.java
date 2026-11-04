/*
 * @test
 * @summary Test for Duration.toNanos() method, covering normal and edge cases.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testToNanosNormal();
        testToNanosNegativeSeconds();
        testToNanosLargeValues();
        testToNanosEdgeCaseMinValue();
    }

    static void testToNanosNormal() {
        Duration d = Duration.ofSeconds(10, 500_000_000); // 10.5 seconds
        long nanos = d.toNanos();
        long expected = 10500000000L;
        if (nanos != expected) {
            throw new AssertionError("Expected " + expected + " but got " + nanos);
        }
    }

    static void testToNanosNegativeSeconds() {
        Duration d = Duration.ofSeconds(-5, -500_000_000); // -5.5 seconds
        long nanos = d.toNanos();
        long expected = -5500000000L;
        if (nanos != expected) {
            throw new AssertionError("Expected " + expected + " but got " + nanos);
        }
    }

    static void testToNanosLargeValues() {
        Duration d = Duration.ofSeconds(Long.MAX_VALUE / 2, 999_999_999);
        long nanos = d.toNanos();
        long expected = Math.multiplyExact(Long.MAX_VALUE / 2, 1000000000L) + 999_999_999L;
        if (nanos != expected) {
            throw new AssertionError("Expected " + expected + " but got " + nanos);
        }
    }

    static void testToNanosEdgeCaseMinValue() {
        try {
            Duration d = Duration.ofSeconds(Long.MIN_VALUE, 0);
            d.toNanos();
            // Should not throw
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected ArithmeticException for Long.MIN_VALUE seconds", e);
        }
    }
}
