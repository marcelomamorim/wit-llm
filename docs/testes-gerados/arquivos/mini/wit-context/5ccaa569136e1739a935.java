/*
 * @test
 * @summary Test java.time.Instant.toEpochMilli() for normal and boundary cases
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.lang.ArithmeticException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testToEpochMilliNormal();
        testToEpochMilliNegativeSecondsPositiveNanos();
    }

    private static void testToEpochMilliNormal() {
        // Normal case: positive seconds and nanos
        Instant instant = Instant.ofEpochSecond(1L, 500_000_000L); // 1.5 seconds
        long expected = 1500L; // 1.5 seconds in milliseconds
        long actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }

        // Zero instant
        instant = Instant.ofEpochSecond(0L, 0L);
        expected = 0L;
        actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }

        // Negative seconds, zero nanos
        instant = Instant.ofEpochSecond(-1L, 0L);
        expected = -1000L;
        actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    private static void testToEpochMilliNegativeSecondsPositiveNanos() {
        // This test tries to cover the branch where seconds < 0 and nanos > 0
        // Use values that do not cause overflow
        Instant instant = Instant.ofEpochSecond(-1L, 500_000_000L); // -0.5 seconds
        long expected = -500L; // -0.5 seconds in milliseconds
        long actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }

        // Test large negative seconds with nanos > 0 but no overflow
        instant = Instant.ofEpochSecond(-1000L, 1_000_000L); // -999.999 seconds
        expected = -999999L;
        actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }
}
