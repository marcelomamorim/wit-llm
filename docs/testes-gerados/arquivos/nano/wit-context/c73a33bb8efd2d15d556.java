/*
 * @test
 * @summary Test the plus(Duration) method of java.time.Duration for expected behavior.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testPlusWithPositiveDuration();
        testPlusWithNegativeDuration();
        testPlusWithZeroDuration();
        testPlusWithLargeValues();
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

    static void testPlusWithNegativeDuration() {
        Duration d1 = Duration.ofSeconds(20, 0); // 20 seconds
        Duration d2 = Duration.ofSeconds(-5, 0); // -5 seconds
        Duration result = d1.plus(d2);
        Duration expected = Duration.ofSeconds(15, 0); // 15 seconds
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plus with negative duration. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusWithZeroDuration() {
        Duration d1 = Duration.ofSeconds(12345, 678_900_000);
        Duration zero = Duration.ZERO;
        Duration result = d1.plus(zero);
        if (!result.equals(d1)) {
            throw new AssertionError("Failed: plus with zero duration. Expected: " + d1 + ", but got: " + result);
        }
    }

    static void testPlusWithLargeValues() {
        Duration d1 = Duration.ofSeconds(Long.MAX_VALUE - 1, 999_999_999);
        Duration d2 = Duration.ofSeconds(1, 1);
        try {
            Duration result = d1.plus(d2);
            // Expect no exception, result should be Long.MAX_VALUE, 0
            Duration expected = Duration.ofSeconds(Long.MAX_VALUE, 0);
            if (!result.equals(expected)) {
                throw new AssertionError("Failed: plus with large values. Expected: " + expected + ", but got: " + result);
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected ArithmeticException for large values", e);
        }
    }
}
