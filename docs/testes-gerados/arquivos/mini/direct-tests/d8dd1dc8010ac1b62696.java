/*
 * @test
 * @summary Test java.time.Duration plusMillis method behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusMillisZero();
        testPlusMillisPositive();
        testPlusMillisNegative();
        testPlusMillisLarge();
    }

    private static void testPlusMillisZero() {
        Duration d = Duration.ZERO.plusMillis(0);
        if (!d.equals(Duration.ZERO)) {
            throw new AssertionError("plusMillis(0) should return Duration.ZERO");
        }
    }

    private static void testPlusMillisPositive() {
        Duration base = Duration.ofSeconds(1, 500_000_000); // 1.5 seconds
        Duration result = base.plusMillis(1500); // add 1.5 seconds
        Duration expected = Duration.ofSeconds(3, 0); // 3 seconds
        if (!result.equals(expected)) {
            throw new AssertionError("plusMillis(1500) failed: expected " + expected + " but got " + result);
        }
    }

    private static void testPlusMillisNegative() {
        Duration base = Duration.ofSeconds(3, 0);
        Duration result = base.plusMillis(-1500); // subtract 1.5 seconds
        Duration expected = Duration.ofSeconds(1, 500_000_000); // 1.5 seconds
        if (!result.equals(expected)) {
            throw new AssertionError("plusMillis(-1500) failed: expected " + expected + " but got " + result);
        }
    }

    private static void testPlusMillisLarge() {
        Duration base = Duration.ZERO;
        long largeMillis = 3_600_000; // 1 hour in milliseconds
        Duration result = base.plusMillis(largeMillis);
        Duration expected = Duration.ofHours(1);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMillis(3600000) failed: expected " + expected + " but got " + result);
        }
    }
}
