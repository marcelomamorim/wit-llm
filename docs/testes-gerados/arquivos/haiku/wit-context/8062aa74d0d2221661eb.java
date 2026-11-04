/*
 * @test
 * @summary Witup-derived tests for Duration.dividedBy(long)
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testDivideByZeroThrows();
        testDivideByOneReturnsSameInstance();
        testExactDivision();
        testPositiveTruncatedDivision();
        testNegativeDividendRoundsTowardZero();
        testNegativeDivisorTruncatedDivision();
        testNanoPrecisionDivision();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDivideByZeroThrows() {
        boolean thrown = false;
        try {
            Duration.ofSeconds(10).dividedBy(0);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "dividedBy(0) must throw ArithmeticException");
    }

    static void testDivideByOneReturnsSameInstance() {
        Duration d = Duration.ofSeconds(7, 5);
        check(d.dividedBy(1) == d, "dividedBy(1) must return this");
    }

    static void testExactDivision() {
        Duration result = Duration.ofSeconds(10).dividedBy(5);
        check(result.equals(Duration.ofSeconds(2)), "10s / 5 must be 2s");
    }

    static void testPositiveTruncatedDivision() {
        Duration result = Duration.ofSeconds(10).dividedBy(3);
        check(result.equals(Duration.ofNanos(3333333333L)),
              "10s / 3 must be truncated to 3.333333333s");
    }

    static void testNegativeDividendRoundsTowardZero() {
        Duration result = Duration.ofSeconds(-10).dividedBy(3);
        check(result.equals(Duration.ofNanos(-3333333333L)),
              "-10s / 3 must round toward zero to -3.333333333s");
    }

    static void testNegativeDivisorTruncatedDivision() {
        Duration result = Duration.ofSeconds(10).dividedBy(-5);
        check(result.equals(Duration.ofSeconds(-2)), "10s / -5 must be -2s");
    }

    static void testNanoPrecisionDivision() {
        Duration result = Duration.ofNanos(10).dividedBy(4);
        check(result.equals(Duration.ofNanos(2)), "10ns / 4 must be 2ns");
    }
}
