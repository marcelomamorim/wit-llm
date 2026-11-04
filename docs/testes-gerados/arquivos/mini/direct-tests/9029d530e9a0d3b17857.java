/*
 * @test
 * @summary Test java.time.LocalDateTime.plusMonths(long) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusMonthsPositive();
        testPlusMonthsNegative();
        testPlusMonthsZero();
        testPlusMonthsYearBoundary();
    }

    private static void testPlusMonthsPositive() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 15, 10, 30);
        LocalDateTime result = ldt.plusMonths(1);
        if (result.getYear() != 2018 || result.getMonth() != Month.FEBRUARY || result.getDayOfMonth() != 15
                || result.getHour() != 10 || result.getMinute() != 30) {
            throw new AssertionError("plusMonths(1) failed: " + result);
        }
    }

    private static void testPlusMonthsNegative() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.MARCH, 31, 23, 59);
        LocalDateTime result = ldt.plusMonths(-1);
        // February 28 or 29 depending on leap year, 2018 is not leap, so 28
        if (result.getYear() != 2018 || result.getMonth() != Month.FEBRUARY || result.getDayOfMonth() != 28
                || result.getHour() != 23 || result.getMinute() != 59) {
            throw new AssertionError("plusMonths(-1) failed: " + result);
        }
    }

    private static void testPlusMonthsZero() {
        LocalDateTime ldt = LocalDateTime.of(2020, Month.FEBRUARY, 29, 0, 0);
        LocalDateTime result = ldt.plusMonths(0);
        if (!result.equals(ldt)) {
            throw new AssertionError("plusMonths(0) should return same LocalDateTime instance or equal: " + result);
        }
    }

    private static void testPlusMonthsYearBoundary() {
        LocalDateTime ldt = LocalDateTime.of(2019, Month.DECEMBER, 31, 12, 0);
        LocalDateTime result = ldt.plusMonths(1);
        if (result.getYear() != 2020 || result.getMonth() != Month.JANUARY || result.getDayOfMonth() != 31
                || result.getHour() != 12 || result.getMinute() != 0) {
            throw new AssertionError("plusMonths crossing year boundary failed: " + result);
        }
    }
}
