/*
 * @test
 * @summary Witup-generated tests for Instant.toEpochMilli exception and boundary behavior
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPositiveSecondsWithNanos() {
        check(Instant.ofEpochSecond(1, 500_000_000).toEpochMilli() == 1500L,
              "expected 1500 ms for 1.5 s");
    }

    static void testZero() {
        check(Instant.ofEpochSecond(0, 0).toEpochMilli() == 0L,
              "expected 0 ms for epoch");
    }

    static void testNegativeWholeSecond() {
        check(Instant.ofEpochSecond(-1, 0).toEpochMilli() == -1000L,
              "expected -1000 ms for -1 s");
    }

    static void testNegativeSecondsWithPositiveNanos() {
        check(Instant.ofEpochSecond(-1, 500_000_000).toEpochMilli() == -500L,
              "expected -500 ms for -0.5 s");
        check(Instant.ofEpochSecond(-2, 999_999_999).toEpochMilli() == -1001L,
              "expected -1001 ms for -1.000000001 s");
    }

    static void testEpochMilliRoundTrip() {
        check(Instant.ofEpochMilli(-1L).toEpochMilli() == -1L,
              "round trip of -1 ms failed");
        check(Instant.ofEpochMilli(123456789L).toEpochMilli() == 123456789L,
              "round trip of 123456789 ms failed");
    }

    static void testMaxOverflowThrows() {
        boolean thrown = false;
        try {
            Instant.MAX.toEpochMilli();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "expected ArithmeticException for Instant.MAX");
    }

    static void testMinOverflowThrows() {
        boolean thrown = false;
        try {
            Instant.MIN.toEpochMilli();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "expected ArithmeticException for Instant.MIN");
    }

    public static void main(String[] args) {
        testPositiveSecondsWithNanos();
        testZero();
        testNegativeWholeSecond();
        testNegativeSecondsWithPositiveNanos();
        testEpochMilliRoundTrip();
        testMaxOverflowThrows();
        testMinOverflowThrows();
    }
}
