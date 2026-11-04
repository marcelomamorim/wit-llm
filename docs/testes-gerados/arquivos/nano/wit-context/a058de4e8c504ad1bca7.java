/*
 * @test
 * @summary Test for YearMonth.minusYears(long)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testMinusYearsNormal();
        testMinusYearsLongMin();
        testMinusYearsLarge();
    }

    static void testMinusYearsNormal() {
        YearMonth ym = YearMonth.of(2020, Month.JULY);
        YearMonth result = ym.minusYears(5);
        if (!result.equals(YearMonth.of(2015, Month.JULY))) {
            throw new AssertionError("Expected 2015-07, got " + result);
        }
    }

    static void testMinusYearsLongMin() {
        YearMonth ym = YearMonth.of(2020, Month.JULY);
        YearMonth result = ym.minusYears(Long.MIN_VALUE);
        // Expecting equivalent to plusYears(Long.MAX_VALUE).plusYears(1)
        YearMonth expected = ym.plusYears(Long.MAX_VALUE).plusYears(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Long.MIN_VALUE minusYears result mismatch: " + result);
        }
    }

    static void testMinusYearsLarge() {
        YearMonth ym = YearMonth.of(2020, Month.JULY);
        long largeValue = 1000000L;
        YearMonth result = ym.minusYears(largeValue);
        YearMonth expected = YearMonth.of(2020 - (int)largeValue, Month.JULY);
        if (!result.equals(expected)) {
            throw new AssertionError("Large minusYears result mismatch: " + result);
        }
    }
}
