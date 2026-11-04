/*
 * @test    @summary Test for Instant.plusSeconds(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {
    public static void main(String[] args) {
        testPlusSecondsZero();
        testPlusSecondsPositive();
        testPlusSecondsNegative();
        testPlusSecondsLarge();
    }

    static void testPlusSecondsZero() {
        Instant original = Instant.ofEpochSecond(1000L);
        Instant result = original.plusSeconds(0L);
        if (!result.equals(original)) {
            throw new AssertionError("plusSeconds(0) should return the same Instant");
        }
    }

    static void testPlusSecondsPositive() {
        Instant original = Instant.ofEpochSecond(1000L);
        long secondsToAdd = 500L;
        Instant expected = Instant.ofEpochSecond(1500L);
        Instant result = original.plusSeconds(secondsToAdd);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(500) did not produce expected Instant");
        }
    }

    static void testPlusSecondsNegative() {
        Instant original = Instant.ofEpochSecond(1000L);
        long secondsToAdd = -300L;
        Instant expected = Instant.ofEpochSecond(700L);
        Instant result = original.plusSeconds(secondsToAdd);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(-300) did not produce expected Instant");
        }
    }

    static void testPlusSecondsLarge() {
        Instant original = Instant.ofEpochSecond(0L);
        long secondsToAdd = Long.MAX_VALUE;
        Instant result = original.plusSeconds(secondsToAdd);
        Instant expected = Instant.ofEpochSecond(Long.MAX_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(Long.MAX_VALUE) did not produce expected Instant");
        }
    }
}
