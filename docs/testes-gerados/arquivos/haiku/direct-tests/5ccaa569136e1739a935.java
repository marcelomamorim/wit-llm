/*
 * @test
 * @summary Behavioral checks for Instant.toEpochMilli including negative-second adjustment and overflow
 * @run main InstantWitupTest
 */

public class InstantWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void checkEquals(long expected, long actual, String message) {
        if (expected != actual) {
            failures++;
            System.err.println("FAILED: " + message + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        checkEquals(0L, java.time.Instant.EPOCH.toEpochMilli(), "EPOCH should map to 0 ms");

        checkEquals(1234L, java.time.Instant.ofEpochMilli(1234L).toEpochMilli(),
                "ofEpochMilli(1234) round trip");

        checkEquals(999L, java.time.Instant.ofEpochSecond(0L, 999_999_999L).toEpochMilli(),
                "0 s + 999999999 ns should truncate to 999 ms");

        checkEquals(-500L, java.time.Instant.ofEpochSecond(-1L, 500_000_000L).toEpochMilli(),
                "-1 s + 0.5 s should be -500 ms");

        checkEquals(-1L, java.time.Instant.ofEpochSecond(-1L, 999_999_999L).toEpochMilli(),
                "-1 s + 999999999 ns should be -1 ms");

        checkEquals(-1L, java.time.Instant.ofEpochMilli(-1L).toEpochMilli(),
                "ofEpochMilli(-1) round trip");

        checkEquals(-1000L, java.time.Instant.ofEpochSecond(-1L).toEpochMilli(),
                "ofEpochSecond(-1) should be -1000 ms");

        try {
            java.time.Instant.MAX.toEpochMilli();
            check(false, "Instant.MAX.toEpochMilli() should overflow");
        } catch (ArithmeticException expected) {
            // expected: Math.multiplyExact overflow
        }

        try {
            java.time.Instant.MIN.toEpochMilli();
            check(false, "Instant.MIN.toEpochMilli() should overflow");
        } catch (ArithmeticException expected) {
            // expected: Math.multiplyExact overflow
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
