/*
 * @test
 * @summary Test java.time.Duration plusSeconds(long) method
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusSecondsZero();
        testPlusSecondsPositive();
        testPlusSecondsNegative();
        testPlusSecondsLargeValue();
    }

    private static void testPlusSecondsZero() {
        Duration d = Duration.ofSeconds(10, 500);
        Duration result = d.plusSeconds(0);
        if (!result.equals(d)) {
            throw new AssertionError("plusSeconds(0) should return equal Duration");
        }
    }

    private static void testPlusSecondsPositive() {
        Duration d = Duration.ofSeconds(10, 500);
        Duration expected = Duration.ofSeconds(15, 500);
        Duration result = d.plusSeconds(5);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(5) did not add seconds correctly");
        }
    }

    private static void testPlusSecondsNegative() {
        Duration d = Duration.ofSeconds(10, 500);
        Duration expected = Duration.ofSeconds(5, 500);
        Duration result = d.plusSeconds(-5);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(-5) did not subtract seconds correctly");
        }
    }

    private static void testPlusSecondsLargeValue() {
        Duration d = Duration.ofSeconds(0, 0);
        long largeSeconds = 1_000_000_000L;
        Duration result = d.plusSeconds(largeSeconds);
        Duration expected = Duration.ofSeconds(largeSeconds, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(large value) did not add seconds correctly");
        }
    }
}
