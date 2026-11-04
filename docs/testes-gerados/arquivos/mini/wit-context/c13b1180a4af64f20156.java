/*
 * @test
 * @summary Deterministic tests for java.time.Instant.minusMillis(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.DateTimeException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testMinusMillisNormal();
        testMinusMillisLongMinValue();
    }

    private static void testMinusMillisNormal() {
        Instant base = Instant.ofEpochSecond(1_000_000_000L, 500_000_000);
        long subtract = 500L;
        Instant result = base.minusMillis(subtract);
        Instant expected = base.plusMillis(-subtract);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMillis normal case failed: expected " + expected + " but got " + result);
        }
    }

    private static void testMinusMillisLongMinValue() {
        Instant base = Instant.ofEpochSecond(0L, 0);
        // According to source, minusMillis(Long.MIN_VALUE) returns plusMillis(Long.MAX_VALUE).plusMillis(1)
        Instant result = base.minusMillis(Long.MIN_VALUE);
        Instant expected = base.plusMillis(Long.MAX_VALUE).plusMillis(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMillis(Long.MIN_VALUE) failed: expected " + expected + " but got " + result);
        }
        // The result should be a valid Instant, no exception expected
        try {
            result.getEpochSecond();
        } catch (DateTimeException ex) {
            throw new AssertionError("minusMillis(Long.MIN_VALUE) produced invalid Instant", ex);
        }
    }
}
