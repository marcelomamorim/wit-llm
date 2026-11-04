/*
 * @test
 * @summary Tests for Duration.dividedBy(long) observable behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testDivideByZeroThrows();
        testDivideByOneReturnsSameInstance();
        testPositiveTruncation();
        testNegativeDivisorTruncatesTowardZero();
        testNegativeDividendTruncatesTowardZero();
        testNanoPrecisionPreserved();
        testZeroDividend();
        testHugeDivisorYieldsZero();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDivideByZeroThrows() {
        try {
            Duration.ofSeconds(5).dividedBy(0);
            throw new RuntimeException("Expected ArithmeticException for divisor 0");
        } catch (ArithmeticException e) {
            check("Cannot divide by zero".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testDivideByOneReturnsSameInstance() {
        Duration d = Duration.ofSeconds(42, 5);
        check(d.dividedBy(1) == d, "dividedBy(1) should return this");
    }

    static void testPositiveTruncation() {
        check(Duration.ofSeconds(10).dividedBy(3).equals(Duration.ofSeconds(3, 333333333)),
              "10s / 3 should be 3.333333333s");
        check(Duration.ofSeconds(7).dividedBy(2).equals(Duration.ofSeconds(3, 500000000)),
              "7s / 2 should be 3.5s");
    }

    static void testNegativeDivisorTruncatesTowardZero() {
        check(Duration.ofSeconds(10).dividedBy(-3).equals(Duration.ofNanos(-3333333333L)),
              "10s / -3 should be -3.333333333s");
    }

    static void testNegativeDividendTruncatesTowardZero() {
        check(Duration.ofSeconds(-10).dividedBy(3).equals(Duration.ofNanos(-3333333333L)),
              "-10s / 3 should be -3.333333333s");
    }

    static void testNanoPrecisionPreserved() {
        check(Duration.ofNanos(10).dividedBy(4).equals(Duration.ofNanos(2)),
              "10ns / 4 should truncate to 2ns");
    }

    static void testZeroDividend() {
        check(Duration.ZERO.dividedBy(5).equals(Duration.ZERO),
              "ZERO / 5 should be ZERO");
    }

    static void testHugeDivisorYieldsZero() {
        check(Duration.ofSeconds(1).dividedBy(Long.MAX_VALUE).equals(Duration.ZERO),
              "1s / Long.MAX_VALUE should truncate to ZERO");
    }
}
