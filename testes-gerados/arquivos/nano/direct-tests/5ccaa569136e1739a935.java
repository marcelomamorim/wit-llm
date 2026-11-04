/*
 * @test
 * @summary Test for Instant.toEpochMilli() method.
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testToEpochMilliPositiveSecondsAndNanos();
        testToEpochMilliNegativeSecondsPositiveNanos();
        testToEpochMilliNegativeSecondsNegativeNanos();
        testToEpochMilliZeroSeconds();
        testToEpochMilliLargeValues();
    }

    static void testToEpochMilliPositiveSecondsAndNanos() {
        Instant instant = Instant.ofEpochSecond(10, 500_000_000); // 10.5 seconds
        long result = instant.toEpochMilli();
        long expected = 10500; // 10.5 seconds in milliseconds
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testToEpochMilliNegativeSecondsPositiveNanos() {
        Instant instant = Instant.ofEpochSecond(-10, 500_000_000); // -10.5 seconds
        long result = instant.toEpochMilli();
        long expected = -10500; // -10.5 seconds in milliseconds
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testToEpochMilliNegativeSecondsNegativeNanos() {
        Instant instant = Instant.ofEpochSecond(-10, -500_000_000); // -10 seconds and -0.5 seconds nanos
        long result = instant.toEpochMilli();
        long expected = -10500; // -10.5 seconds in milliseconds
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testToEpochMilliZeroSeconds() {
        Instant instant = Instant.ofEpochSecond(0, 0);
        long result = instant.toEpochMilli();
        long expected = 0;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testToEpochMilliLargeValues() {
        Instant instant = Instant.ofEpochSecond(Long.MAX_VALUE / 1000, 999_999_999);
        long result = instant.toEpochMilli();
        long expected = Math.multiplyExact(Long.MAX_VALUE / 1000, 1000) + 999;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
