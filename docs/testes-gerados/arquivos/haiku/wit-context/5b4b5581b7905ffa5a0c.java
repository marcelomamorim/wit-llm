/*
 * @test
 * @summary WITUP-derived behavioral tests for Duration.of(long, TemporalUnit)
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DurationWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullUnitThrowsNPE() {
        boolean thrown = false;
        try {
            Duration.of(1L, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Duration.of(1, null) must throw NullPointerException");
    }

    static void testNullUnitThrowsNPEForZeroAmount() {
        boolean thrown = false;
        try {
            Duration.of(0L, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Duration.of(0, null) must throw NullPointerException");
    }

    static void testSecondsUnit() {
        Duration d = Duration.of(5L, ChronoUnit.SECONDS);
        check(d.equals(Duration.ofSeconds(5L)), "of(5, SECONDS) must equal ofSeconds(5)");
    }

    static void testNanosUnit() {
        Duration d = Duration.of(7L, ChronoUnit.NANOS);
        check(d.equals(Duration.ofNanos(7L)), "of(7, NANOS) must equal ofNanos(7)");
    }

    static void testDaysUnit() {
        Duration d = Duration.of(3L, ChronoUnit.DAYS);
        check(d.getSeconds() == 3L * 86400L, "of(3, DAYS) must have 259200 seconds");
        check(d.getNano() == 0, "of(3, DAYS) must have zero nanos");
    }

    static void testZeroAmountReturnsZero() {
        Duration d = Duration.of(0L, ChronoUnit.SECONDS);
        check(d.isZero(), "of(0, SECONDS) must be zero");
    }

    static void testNegativeAmount() {
        Duration d = Duration.of(-2L, ChronoUnit.SECONDS);
        check(d.equals(Duration.ofSeconds(-2L)), "of(-2, SECONDS) must equal ofSeconds(-2)");
    }

    static void testEstimatedUnitRejected() {
        boolean thrown = false;
        try {
            Duration.of(1L, ChronoUnit.MONTHS);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "of(1, MONTHS) must throw UnsupportedTemporalTypeException");
    }

    static void testDaysOverflowRejected() {
        boolean thrown = false;
        try {
            Duration.of(Long.MAX_VALUE, ChronoUnit.DAYS);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "of(Long.MAX_VALUE, DAYS) must throw ArithmeticException");
    }

    public static void main(String[] args) {
        testNullUnitThrowsNPE();
        testNullUnitThrowsNPEForZeroAmount();
        testSecondsUnit();
        testNanosUnit();
        testDaysUnit();
        testZeroAmountReturnsZero();
        testNegativeAmount();
        testEstimatedUnitRejected();
        testDaysOverflowRejected();
    }
}
