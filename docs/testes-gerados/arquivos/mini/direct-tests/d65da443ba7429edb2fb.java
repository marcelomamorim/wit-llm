/*
 * @test
 * @summary Test java.time.LocalDateTime.minusMonths(long) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusMonthsZero();
        testMinusMonthsPositive();
        testMinusMonthsNegative();
        testMinusMonthsLongMinValue();
    }

    private static void testMinusMonthsZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 15, 10, 30);
        LocalDateTime result = dt.minusMonths(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("minusMonths(0) should return the same LocalDateTime instance");
        }
    }

    private static void testMinusMonthsPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.MARCH, 31, 23, 59);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 31, 23, 59);
        LocalDateTime result = dt.minusMonths(2L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(2) did not produce expected result: " + result);
        }
    }

    private static void testMinusMonthsNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 31, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.MARCH, 31, 0, 0);
        LocalDateTime result = dt.minusMonths(-2L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(-2) did not produce expected result: " + result);
        }
    }

    private static void testMinusMonthsLongMinValue() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JUNE, 15, 12, 0);
        // minusMonths(Long.MIN_VALUE) internally calls plusMonths(Long.MAX_VALUE).plusMonths(1)
        LocalDateTime result = dt.minusMonths(Long.MIN_VALUE);
        // We test that result is not null and is a valid LocalDateTime
        if (result == null) {
            throw new AssertionError("minusMonths(Long.MIN_VALUE) returned null");
        }
        // We test that result is different from original dt
        if (result.equals(dt)) {
            throw new AssertionError("minusMonths(Long.MIN_VALUE) should not return the same instance");
        }
    }
}
