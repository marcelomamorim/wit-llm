/*
 * @test
 * @summary Test java.time.YearMonth.plusMonths(long) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.temporal.ChronoField;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsPositive();
        testPlusMonthsNegative();
        testPlusMonthsYearBoundary();
    }

    static void testPlusMonthsZero() {
        YearMonth ym = YearMonth.of(2020, Month.JANUARY);
        YearMonth result = ym.plusMonths(0L);
        if (result != ym) {
            throw new AssertionError("plusMonths(0) should return same instance");
        }
    }

    static void testPlusMonthsPositive() {
        YearMonth ym = YearMonth.of(2020, Month.JANUARY);
        YearMonth result = ym.plusMonths(15L); // 1 year and 3 months later
        if (result.getYear() != 2021 || result.getMonthValue() != 4) {
            throw new AssertionError("plusMonths(15) failed: " + result);
        }
    }

    static void testPlusMonthsNegative() {
        YearMonth ym = YearMonth.of(2020, Month.MARCH);
        YearMonth result = ym.plusMonths(-4L); // 4 months before March 2020
        if (result.getYear() != 2019 || result.getMonthValue() != 11) {
            throw new AssertionError("plusMonths(-4) failed: " + result);
        }
    }

    static void testPlusMonthsYearBoundary() {
        YearMonth ym = YearMonth.of(1, Month.DECEMBER);
        YearMonth result = ym.plusMonths(1L); // should wrap to year 2, month 1
        if (result.getYear() != 2 || result.getMonthValue() != 1) {
            throw new AssertionError("plusMonths crossing year boundary failed: " + result);
        }
    }
}
