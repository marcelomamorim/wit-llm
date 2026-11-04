/*
 * @test
 * @summary Tests for java.time.Instant.minusMillis(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testMinusMillisNormal();
        testMinusMillisLongMinValue();
        testMinusMillisLargeValue();
        testMinusMillisZero();
    }

    static void testMinusMillisNormal() {
        Instant now = Instant.ofEpochSecond(1000L, 500L);
        Instant result = now.minusMillis(1500L);
        Instant expected = now.plusMillis(-1500L);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusMillis with normal value");
        }
    }

    static void testMinusMillisLongMinValue() {
        Instant now = Instant.ofEpochSecond(1000L, 0L);
        Instant result = now.minusMillis(Long.MIN_VALUE);
        Instant expected = now.plusMillis(Long.MAX_VALUE).plusMillis(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusMillis with Long.MIN_VALUE");
        }
    }

    static void testMinusMillisLargeValue() {
        Instant now = Instant.ofEpochSecond(0L, 0L);
        Instant result = now.minusMillis(999999999L);
        Instant expected = now.plusMillis(-999999999L);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: minusMillis with large value");
        }
    }

    static void testMinusMillisZero() {
        Instant now = Instant.ofEpochSecond(12345L, 6789L);
        Instant result = now.minusMillis(0L);
        if (!result.equals(now)) {
            throw new AssertionError("Failed: minusMillis with zero");
        }
    }
}
