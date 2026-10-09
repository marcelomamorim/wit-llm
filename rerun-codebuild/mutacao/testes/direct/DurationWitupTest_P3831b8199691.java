/*
 * @test
 * @summary Test for Duration.dividedBy(long)
 * @run main DurationWitupTest_P3831b8199691
 */

import java.time.Duration;

public class DurationWitupTest_P3831b8199691 {

    public static void main(String[] args) {
        testDividedByNormal();
        testDividedByZero();
        testDividedByOne();
    }

    static void testDividedByNormal() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(2);
        long seconds = result.getSeconds();
        if (seconds != 5) {
            throw new AssertionError("Expected 5 seconds, but got: " + seconds);
        }
    }

    static void testDividedByZero() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.dividedBy(0);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testDividedByOne() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(1);
        long seconds = result.getSeconds();
        if (seconds != 10) {
            throw new AssertionError("Expected 10 seconds, but got: " + seconds);
        }
    }
}
