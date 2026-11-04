/*
 * @test
 * @summary Test Instant.minusNanos(long) for normal and edge cases including Long.MIN_VALUE
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.DateTimeException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testMinusNanosNormal();
        testMinusNanosLongMinValue();
    }

    private static void testMinusNanosNormal() {
        Instant base = Instant.ofEpochSecond(1, 500);
        // Subtract 0 nanos, result should be equal to base
        Instant result = base.minusNanos(0);
        if (!result.equals(base)) {
            throw new AssertionError("minusNanos(0) should return the same Instant");
        }

        // Subtract positive nanos less than 1 second
        result = base.minusNanos(499);
        if (result.getEpochSecond() != 1 || result.getNano() != 1) {
            throw new AssertionError("minusNanos(499) failed: " + result);
        }

        // Subtract nanos crossing second boundary
        result = base.minusNanos(1000);
        if (result.getEpochSecond() != 0 || result.getNano() != 999_500_000) {
            throw new AssertionError("minusNanos(1000) failed: " + result);
        }

        // Subtract large nanos (several seconds)
        long nanosToSubtract = 3_000_000_000L; // 3 seconds
        result = base.minusNanos(nanosToSubtract);
        if (result.getEpochSecond() != -2 || result.getNano() != 500) {
            throw new AssertionError("minusNanos(3_000_000_000) failed: " + result);
        }
    }

    private static void testMinusNanosLongMinValue() {
        Instant base = Instant.ofEpochSecond(0, 0);
        // According to source, minusNanos(Long.MIN_VALUE) returns plusNanos(Long.MAX_VALUE).plusNanos(1)
        Instant result = base.minusNanos(Long.MIN_VALUE);
        Instant expected = base.plusNanos(Long.MAX_VALUE).plusNanos(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(Long.MIN_VALUE) did not match expected result");
        }

        // Test that minusNanos(Long.MIN_VALUE) does not throw but returns a valid Instant
        try {
            result.getEpochSecond();
            result.getNano();
        } catch (DateTimeException | ArithmeticException e) {
            throw new AssertionError("minusNanos(Long.MIN_VALUE) threw unexpected exception: " + e);
        }
    }
}
