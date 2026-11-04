/*
 * @test
 * @summary Tests the dividedBy(Duration) method of java.time.Duration for correctness and exception handling.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testDividedByNormalCase();
        testDividedByZeroDuration();
        testDividedByNull();
        testDividedByDifferentDurations();
    }

    static void testDividedByNormalCase() {
        Duration duration = Duration.ofSeconds(10);
        Duration divisor = Duration.ofSeconds(2);
        long result = duration.dividedBy(divisor);
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    static void testDividedByZeroDuration() {
        Duration duration = Duration.ofSeconds(10);
        Duration divisor = Duration.ofSeconds(0);
        try {
            duration.dividedBy(divisor);
            throw new AssertionError("Expected ArithmeticException for division by zero duration");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testDividedByNull() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.dividedBy(null);
            throw new AssertionError("Expected NullPointerException for null divisor");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDividedByDifferentDurations() {
        Duration duration = Duration.ofSeconds(15);
        Duration divisor = Duration.ofSeconds(4);
        long result = duration.dividedBy(divisor);
        if (result != 3) {
            throw new AssertionError("Expected 3, but got " + result);
        }
    }
}
