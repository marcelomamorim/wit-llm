/*
 * @test
 * @summary Tests for Duration.dividedBy(long)
 * @run main DurationWitupTest_P3831b8199691
 */

import java.time.Duration;
import java.math.BigDecimal;

public class DurationWitupTest_P3831b8199691 {

    public static void main(String[] args) {
        testDividedByZero();
        testDividedByOne();
        testDividedByPositive();
        testDividedByNegative();
    }

    static void testDividedByZero() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.dividedBy(0);
            System.err.println("Failed: Expected ArithmeticException for divisor 0");
        } catch (ArithmeticException e) {
            System.out.println("Passed: ArithmeticException thrown for divisor 0");
        }
    }

    static void testDividedByOne() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(1);
        if (result.equals(duration)) {
            System.out.println("Passed: dividedBy(1) returns the same Duration");
        } else {
            System.err.println("Failed: dividedBy(1) does not return the same Duration");
        }
    }

    static void testDividedByPositive() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(2);
        Duration expected = Duration.ofSeconds(5);
        if (result.equals(expected)) {
            System.out.println("Passed: dividedBy(2) correctly halves the duration");
        } else {
            System.err.println("Failed: dividedBy(2) did not produce expected result");
        }
    }

    static void testDividedByNegative() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(-2);
        Duration expected = Duration.ofSeconds(-5);
        if (result.equals(expected)) {
            System.out.println("Passed: dividedBy(-2) correctly negates the duration");
        } else {
            System.err.println("Failed: dividedBy(-2) did not produce expected result");
        }
    }
}
