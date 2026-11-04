/*
 * @test
 * @summary Test java.time.Instant.minusMillis(long) method behavior
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        testMinusMillisZero();
        testMinusMillisPositive();
        testMinusMillisNegative();
        testMinusMillisLongMinValue();
    }

    private static void testMinusMillisZero() {
        Instant now = Instant.now();
        Instant result = now.minusMillis(0L);
        if (!result.equals(now)) {
            throw new AssertionError("minusMillis(0) should return the same Instant");
        }
    }

    private static void testMinusMillisPositive() {
        Instant base = Instant.ofEpochSecond(1, 500_000_000); // 1.5 seconds
        Instant expected = base.minusMillis(1).plusMillis(-1); // minusMillis(1) internally calls plusMillis(-1)
        Instant result = base.minusMillis(1);
        if (!result.equals(base.plusMillis(-1))) {
            throw new AssertionError("minusMillis(positive) did not produce expected result");
        }
    }

    private static void testMinusMillisNegative() {
        Instant base = Instant.ofEpochSecond(2, 0);
        Instant result = base.minusMillis(-10);
        Instant expected = base.plusMillis(10);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMillis(negative) should add millis");
        }
    }

    private static void testMinusMillisLongMinValue() {
        Instant base = Instant.ofEpochSecond(0, 0);
        // According to code, minusMillis(Long.MIN_VALUE) returns plusMillis(Long.MAX_VALUE).plusMillis(1)
        Instant expected = base.plusMillis(Long.MAX_VALUE).plusMillis(1);
        Instant result = base.minusMillis(Long.MIN_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMillis(Long.MIN_VALUE) did not produce expected result");
        }
    }
}
