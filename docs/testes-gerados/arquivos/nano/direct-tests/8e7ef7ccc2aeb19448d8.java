/*
 * @test
 * @summary Test Duration.toNanos() method for correct behavior.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testToNanosPositiveSeconds();
        testToNanosNegativeSeconds();
        testToNanosZeroDuration();
        testToNanosLargeDuration();
    }

    static void testToNanosPositiveSeconds() {
        Duration duration = Duration.ofSeconds(10);
        long nanos = duration.toNanos();
        long expected = 10 * 1000000000L;
        if (nanos != expected) {
            throw new AssertionError("Expected " + expected + " but got " + nanos);
        }
    }

    static void testToNanosNegativeSeconds() {
        Duration duration = Duration.ofSeconds(-5);
        long nanos = duration.toNanos();
        long expected = -5 * 1000000000L;
        if (nanos != expected) {
            throw new AssertionError("Expected " + expected + " but got " + nanos);
        }
    }

    static void testToNanosZeroDuration() {
        Duration duration = Duration.ZERO;
        long nanos = duration.toNanos();
        if (nanos != 0) {
            throw new AssertionError("Expected 0 but got " + nanos);
        }
    }

    static void testToNanosLargeDuration() {
        Duration duration = Duration.ofSeconds(Long.MAX_VALUE, 999999999);
        long nanos = duration.toNanos();
        long expected = Math.multiplyExact(Long.MAX_VALUE, 1000000000L) + 999999999L;
        if (nanos != expected) {
            throw new AssertionError("Expected " + expected + " but got " + nanos);
        }
    }
}
