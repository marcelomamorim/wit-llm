/*
 * @test
 * @summary Test Duration.plus(Duration) method for correctness and regressions
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testPlusWithPositiveDuration();
        testPlusWithZeroDuration();
        testPlusWithNegativeDuration();
        testPlusWithNull();
    }

    static void testPlusWithPositiveDuration() {
        Duration d1 = Duration.ofSeconds(10, 500_000_000); // 10.5 seconds
        Duration d2 = Duration.ofSeconds(5, 250_000_000);  // 5.25 seconds
        Duration result = d1.plus(d2);
        Duration expected = Duration.ofSeconds(15, 750_000_000); // 15.75 seconds
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plus with positive durations. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusWithZeroDuration() {
        Duration d1 = Duration.ofSeconds(20, 0);
        Duration zero = Duration.ZERO;
        Duration result = d1.plus(zero);
        if (!result.equals(d1)) {
            throw new AssertionError("Failed: plus with zero duration. Expected: " + d1 + ", but got: " + result);
        }
    }

    static void testPlusWithNegativeDuration() {
        Duration d1 = Duration.ofSeconds(10, 0);
        Duration d2 = Duration.ofSeconds(-5, 0);
        Duration result = d1.plus(d2);
        Duration expected = Duration.ofSeconds(5, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plus with negative duration. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusWithNull() {
        Duration d1 = Duration.ofSeconds(10);
        try {
            d1.plus((Duration) null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
