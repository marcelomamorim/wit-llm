/*
 * @test
 * @summary Test java.time.Duration.toNanos() method behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPositiveDuration();
        testZeroDuration();
        testNegativeDuration();
        testLongMinValueSeconds();
    }

    private static void testPositiveDuration() {
        Duration d = Duration.ofSeconds(2, 500);
        long expected = 2L * 1000000000L + 500L;
        long actual = d.toNanos();
        if (actual != expected) {
            throw new AssertionError("Positive duration toNanos failed: expected " + expected + " but got " + actual);
        }
    }

    private static void testZeroDuration() {
        Duration d = Duration.ofSeconds(0, 0);
        long actual = d.toNanos();
        if (actual != 0L) {
            throw new AssertionError("Zero duration toNanos failed: expected 0 but got " + actual);
        }
    }

    private static void testNegativeDuration() {
        Duration d = Duration.ofSeconds(-2, 500);
        // According to the method, tempSeconds = -2, tempNanos = 500
        // Since tempSeconds < 0, tempSeconds = -1, tempNanos = 500 - 1_000_000_000 = -999_999_500
        // totalNanos = (-1 * 1_000_000_000) + (-999_999_500) = -1_999_999_500
        long expected = -1999999500L;
        long actual = d.toNanos();
        if (actual != expected) {
            throw new AssertionError("Negative duration toNanos failed: expected " + expected + " but got " + actual);
        }
    }

    private static void testLongMinValueSeconds() {
        // Test with seconds = Long.MIN_VALUE and nanos = 0
        Duration d = Duration.ofSeconds(Long.MIN_VALUE, 0);
        // According to the method, tempSeconds = Long.MIN_VALUE, tempNanos = 0
        // tempSeconds < 0, so tempSeconds = Long.MIN_VALUE + 1, tempNanos = 0 - 1_000_000_000
        // totalNanos = multiplyExact(tempSeconds, 1_000_000_000) + tempNanos
        // This should not overflow and should produce a valid long
        long tempSeconds = Long.MIN_VALUE + 1;
        long tempNanos = -1000000000L;
        long expected = Math.multiplyExact(tempSeconds, 1000000000L) + tempNanos;
        long actual = d.toNanos();
        if (actual != expected) {
            throw new AssertionError("Long.MIN_VALUE seconds toNanos failed: expected " + expected + " but got " + actual);
        }
    }
}
