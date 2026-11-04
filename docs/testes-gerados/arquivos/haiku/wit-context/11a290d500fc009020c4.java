/*
 * @test
 * @summary Witup-generated tests for Duration.dividedBy(Duration)
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        testNullDivisorThrowsNPE();
        testPositiveQuotientTruncates();
        testNegativeDividendTruncatesTowardZero();
        testFractionalSecondsQuotient();
        testNanosQuotient();
        testZeroDivisorThrowsArithmeticException();
        testQuotientOverflowThrowsArithmeticException();
    }

    static void testNullDivisorThrowsNPE() {
        Duration dividend = Duration.of(10, ChronoUnit.SECONDS);
        try {
            dividend.dividedBy(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null divisor");
    }

    static void testPositiveQuotientTruncates() {
        Duration dividend = Duration.of(10, ChronoUnit.SECONDS);
        Duration divisor = Duration.of(3, ChronoUnit.SECONDS);
        check(3L, dividend.dividedBy(divisor), "10s / 3s");
    }

    static void testNegativeDividendTruncatesTowardZero() {
        Duration dividend = Duration.of(-10, ChronoUnit.SECONDS);
        Duration divisor = Duration.of(3, ChronoUnit.SECONDS);
        check(-3L, dividend.dividedBy(divisor), "-10s / 3s");
    }

    static void testFractionalSecondsQuotient() {
        Duration dividend = Duration.of(1500, ChronoUnit.MILLIS);
        Duration divisor = Duration.of(500, ChronoUnit.MILLIS);
        check(3L, dividend.dividedBy(divisor), "1500ms / 500ms");
    }

    static void testNanosQuotient() {
        Duration dividend = Duration.of(1, ChronoUnit.SECONDS);
        Duration divisor = Duration.of(1, ChronoUnit.NANOS);
        check(1000000000L, dividend.dividedBy(divisor), "1s / 1ns");
    }

    static void testZeroDivisorThrowsArithmeticException() {
        Duration dividend = Duration.of(10, ChronoUnit.SECONDS);
        Duration divisor = Duration.of(0, ChronoUnit.SECONDS);
        try {
            dividend.dividedBy(divisor);
        } catch (ArithmeticException expected) {
            return;
        }
        throw new RuntimeException("Expected ArithmeticException for zero divisor");
    }

    static void testQuotientOverflowThrowsArithmeticException() {
        Duration dividend = Duration.of(Long.MAX_VALUE, ChronoUnit.SECONDS);
        Duration divisor = Duration.of(1, ChronoUnit.NANOS);
        try {
            dividend.dividedBy(divisor);
        } catch (ArithmeticException expected) {
            return;
        }
        throw new RuntimeException("Expected ArithmeticException for quotient overflow");
    }

    static void check(long expected, long actual, String label) {
        if (expected != actual) {
            throw new RuntimeException(label + ": expected " + expected + " but was " + actual);
        }
    }
}
