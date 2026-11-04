/*
 * @test
 * @summary Test java.time.Instant plusSeconds method
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        testPlusSecondsZero();
        testPlusSecondsPositive();
        testPlusSecondsNegative();
        testPlusSecondsLargeValue();
    }

    private static void testPlusSecondsZero() {
        Instant base = Instant.ofEpochSecond(1000L);
        Instant result = base.plusSeconds(0L);
        if (!result.equals(base)) {
            throw new AssertionError("plusSeconds(0) should return the same Instant");
        }
    }

    private static void testPlusSecondsPositive() {
        Instant base = Instant.ofEpochSecond(1000L);
        Instant expected = Instant.ofEpochSecond(1005L);
        Instant result = base.plusSeconds(5L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(5) did not add 5 seconds correctly");
        }
    }

    private static void testPlusSecondsNegative() {
        Instant base = Instant.ofEpochSecond(1000L);
        Instant expected = Instant.ofEpochSecond(995L);
        Instant result = base.plusSeconds(-5L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(-5) did not subtract 5 seconds correctly");
        }
    }

    private static void testPlusSecondsLargeValue() {
        Instant base = Instant.ofEpochSecond(0L);
        long largeSeconds = 31536000L; // 1 year in seconds (non-leap)
        Instant expected = Instant.ofEpochSecond(largeSeconds);
        Instant result = base.plusSeconds(largeSeconds);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(largeSeconds) did not add correctly");
        }
    }
}
