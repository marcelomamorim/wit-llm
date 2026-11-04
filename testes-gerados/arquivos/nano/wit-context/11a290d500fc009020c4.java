/*
 * @test
 * @summary Test for Duration.dividedBy(Duration divisor) method.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testDividedByNormal();
        testDividedByNull();
        testDividedBySelf();
        testDividedByZeroDuration();
    }

    static void testDividedByNormal() {
        Duration duration1 = Duration.ofSeconds(10);
        Duration duration2 = Duration.ofSeconds(2);
        long result = duration1.dividedBy(duration2);
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
    }

    static void testDividedByNull() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.dividedBy(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDividedBySelf() {
        Duration duration = Duration.ofSeconds(10);
        long result = duration.dividedBy(duration);
        if (result != 1) {
            throw new AssertionError("Expected 1, but got " + result);
        }
    }

    static void testDividedByZeroDuration() {
        Duration zeroDuration = Duration.ofSeconds(0);
        Duration nonZeroDuration = Duration.ofSeconds(5);
        try {
            zeroDuration.dividedBy(nonZeroDuration);
            // division by zero should throw ArithmeticException
            throw new AssertionError("Expected ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
