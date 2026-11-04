/*
 * @test
 * @summary Test java.time.Instant plusMillis method behavior
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        testPlusMillisZero();
        testPlusMillisPositive();
        testPlusMillisNegative();
        testPlusMillisLargeValue();
    }

    private static void testPlusMillisZero() {
        Instant base = Instant.EPOCH;
        Instant result = base.plusMillis(0L);
        if (!result.equals(base)) {
            throw new AssertionError("plusMillis(0) should return the same Instant");
        }
    }

    private static void testPlusMillisPositive() {
        Instant base = Instant.EPOCH;
        Instant expected = Instant.ofEpochSecond(1, 500_000_000L); // 1.5 seconds
        Instant result = base.plusMillis(1500L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMillis(1500) did not produce expected Instant");
        }
    }

    private static void testPlusMillisNegative() {
        Instant base = Instant.ofEpochSecond(2, 0);
        Instant expected = Instant.ofEpochSecond(1, 500_000_000L); // 1.5 seconds
        Instant result = base.plusMillis(-500L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMillis(-500) did not produce expected Instant");
        }
    }

    private static void testPlusMillisLargeValue() {
        Instant base = Instant.EPOCH;
        long largeMillis = 3_600_000L; // 1 hour in millis
        Instant expected = Instant.ofEpochSecond(3600, 0);
        Instant result = base.plusMillis(largeMillis);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMillis(3600000) did not produce expected Instant");
        }
    }
}
