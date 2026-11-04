/*
 * @test
 * @summary Test java.time.Instant.minusNanos(long) method behavior
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        testMinusNanosZero();
        testMinusNanosPositive();
        testMinusNanosNegative();
        testMinusNanosLongMinValue();
    }

    private static void testMinusNanosZero() {
        Instant base = Instant.ofEpochSecond(1, 500);
        Instant result = base.minusNanos(0L);
        if (!result.equals(base)) {
            throw new AssertionError("minusNanos(0) should return the same Instant");
        }
    }

    private static void testMinusNanosPositive() {
        Instant base = Instant.ofEpochSecond(2, 1000);
        Instant expected = base.plusNanos(-1234L);
        Instant result = base.minusNanos(1234L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(positive) did not produce expected Instant");
        }
    }

    private static void testMinusNanosNegative() {
        Instant base = Instant.ofEpochSecond(3, 2000);
        Instant expected = base.plusNanos(5678L);
        Instant result = base.minusNanos(-5678L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(negative) did not produce expected Instant");
        }
    }

    private static void testMinusNanosLongMinValue() {
        Instant base = Instant.ofEpochSecond(4, 3000);
        // According to source, minusNanos(Long.MIN_VALUE) returns plusNanos(Long.MAX_VALUE).plusNanos(1)
        Instant expected = base.plusNanos(Long.MAX_VALUE).plusNanos(1);
        Instant result = base.minusNanos(Long.MIN_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(Long.MIN_VALUE) did not produce expected Instant");
        }
    }
}
