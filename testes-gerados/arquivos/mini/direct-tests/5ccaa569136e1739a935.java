/*
 * @test
 * @summary Test java.time.Instant.toEpochMilli() method behavior
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        testPositiveSeconds();
        testZeroSecondsZeroNanos();
        testNegativeSecondsZeroNanos();
        testNegativeSecondsPositiveNanos();
        testLargeValues();
        System.out.println("All tests passed.");
    }

    private static void testPositiveSeconds() {
        Instant instant = Instant.ofEpochSecond(1, 500_000_000); // 1.5 seconds
        long expected = 1500L; // 1 second = 1000 ms + 500 ms
        long actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("testPositiveSeconds failed: expected " + expected + " but got " + actual);
        }
    }

    private static void testZeroSecondsZeroNanos() {
        Instant instant = Instant.ofEpochSecond(0, 0);
        long expected = 0L;
        long actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("testZeroSecondsZeroNanos failed: expected " + expected + " but got " + actual);
        }
    }

    private static void testNegativeSecondsZeroNanos() {
        Instant instant = Instant.ofEpochSecond(-1, 0);
        long expected = -1000L;
        long actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("testNegativeSecondsZeroNanos failed: expected " + expected + " but got " + actual);
        }
    }

    private static void testNegativeSecondsPositiveNanos() {
        // seconds < 0 and nanos > 0 triggers special branch
        Instant instant = Instant.ofEpochSecond(-1, 500_000_000); // -0.5 seconds
        // Calculation:
        // millis = (seconds + 1) * 1000 = 0
        // adjustment = nanos/1_000_000 - 1000 = 500 - 1000 = -500
        // result = 0 + (-500) = -500
        long expected = -500L;
        long actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("testNegativeSecondsPositiveNanos failed: expected " + expected + " but got " + actual);
        }
    }

    private static void testLargeValues() {
        // Test large positive value
        Instant instant = Instant.ofEpochSecond(1_000_000_000L, 999_999_999);
        long expected = 1_000_000_000_999L; // seconds * 1000 + nanos/1_000_000
        long actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("testLargeValues positive failed: expected " + expected + " but got " + actual);
        }

        // Test large negative value with nanos > 0
        instant = Instant.ofEpochSecond(-1_000_000_000L, 1_000_000);
        // millis = (seconds + 1) * 1000 = (-999_999_999) * 1000 = -999_999_999_000
        // adjustment = nanos/1_000_000 - 1000 = 1 - 1000 = -999
        // result = -999_999_999_000 + (-999) = -999_999_999_999
        expected = -999_999_999_999L;
        actual = instant.toEpochMilli();
        if (actual != expected) {
            throw new AssertionError("testLargeValues negative failed: expected " + expected + " but got " + actual);
        }
    }
}
