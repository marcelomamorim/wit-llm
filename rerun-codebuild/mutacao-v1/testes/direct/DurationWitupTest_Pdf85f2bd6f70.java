/*
 * @test
 * @summary Test Duration.dividedBy(long) method for various inputs
 * @run main DurationWitupTest_Pdf85f2bd6f70
 */

import java.time.Duration;
import java.math.BigDecimal;

public class DurationWitupTest_Pdf85f2bd6f70 {

    public static void main(String[] args) {
        testDividedByNormal();
        testDividedByZero();
        testDividedByOne();
        testDividedByNegative();
        System.out.println("All tests passed.");
    }

    static void testDividedByNormal() {
        Duration dur = Duration.ofSeconds(10);
        Duration result = dur.dividedBy(2);
        if (!result.equals(Duration.ofSeconds(5))) {
            throw new AssertionError("Failed: dividedBy(2) on 10s should be 5s");
        }
        result = dur.dividedBy(3);
        BigDecimal expectedSeconds = dur.toBigDecimalSeconds().divide(BigDecimal.valueOf(3), java.math.RoundingMode.DOWN);
        Duration expected = create(expectedSeconds);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: dividedBy(3) on 10s");
        }
    }

    static void testDividedByZero() {
        Duration dur = Duration.ofSeconds(10);
        try {
            dur.dividedBy(0);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testDividedByOne() {
        Duration dur = Duration.ofSeconds(12345);
        Duration result = dur.dividedBy(1);
        if (!result.equals(dur)) {
            throw new AssertionError("Failed: dividedBy(1) should return the same Duration");
        }
    }

    static void testDividedByNegative() {
        Duration dur = Duration.ofSeconds(20);
        Duration result = dur.dividedBy(-2);
        BigDecimal expectedSeconds = dur.toBigDecimalSeconds().divide(BigDecimal.valueOf(-2), java.math.RoundingMode.DOWN);
        Duration expected = create(expectedSeconds);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: dividedBy(-2) on 20s");
        }
    }

    static Duration create(BigDecimal seconds) {
        BigInteger secPart = seconds.toBigInteger();
        BigDecimal fractional = seconds.subtract(new BigDecimal(secPart));
        long nanos = fractional.multiply(BigDecimal.valueOf(1_000_000_000)).longValue();
        return Duration.ofSeconds(secPart.longValue(), nanos);
    }
}
