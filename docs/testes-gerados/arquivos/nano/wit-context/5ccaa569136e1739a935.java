/*
 * @test
 * @summary Tests for java.time.Instant.toEpochMilli()
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testToEpochMilliPositiveSecondsAndNanos();
        testToEpochMilliNegativeSecondsAndPositiveNanos();
        testToEpochMilliNegativeSecondsAndNegativeNanos();
        testToEpochMilliZeroSeconds();
        testToEpochMilliZeroNanos();
    }

    private static void testToEpochMilliPositiveSecondsAndNanos() {
        Instant instant = Instant.ofEpochSecond(10, 500_000_000); // 10s + 0.5s
        long expectedMillis = 10000 + 500;
        long actualMillis = instant.toEpochMilli();
        if (actualMillis != expectedMillis) {
            throw new AssertionError("Expected: " + expectedMillis + ", but got: " + actualMillis);
        }
    }

    private static void testToEpochMilliNegativeSecondsAndPositiveNanos() {
        Instant instant = Instant.ofEpochSecond(-10, 500_000_000); // -10s + 0.5s
        long expectedMillis = -10000 + 500;
        long actualMillis = instant.toEpochMilli();
        if (actualMillis != expectedMillis) {
            throw new AssertionError("Expected: " + expectedMillis + ", but got: " + actualMillis);
        }
    }

    private static void testToEpochMilliNegativeSecondsAndNegativeNanos() {
        Instant instant = Instant.ofEpochSecond(-10, -500_000_000); // -10s - 0.5s
        long expectedMillis = -10000 - 500;
        long actualMillis = instant.toEpochMilli();
        if (actualMillis != expectedMillis) {
            throw new AssertionError("Expected: " + expectedMillis + ", but got: " + actualMillis);
        }
    }

    private static void testToEpochMilliZeroSeconds() {
        Instant instant = Instant.ofEpochSecond(0, 0);
        long expectedMillis = 0;
        long actualMillis = instant.toEpochMilli();
        if (actualMillis != expectedMillis) {
            throw new AssertionError("Expected: " + expectedMillis + ", but got: " + actualMillis);
        }
    }

    private static void testToEpochMilliZeroNanos() {
        Instant instant = Instant.ofEpochSecond(123, 0);
        long expectedMillis = 123000;
        long actualMillis = instant.toEpochMilli();
        if (actualMillis != expectedMillis) {
            throw new AssertionError("Expected: " + expectedMillis + ", but got: " + actualMillis);
        }
    }
}
