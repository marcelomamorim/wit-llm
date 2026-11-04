/*
 * @test
 * @summary Test YearMonth.minusMonths(long) method behavior
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testMinusMonthsZero();
        testMinusMonthsPositive();
        testMinusMonthsNegative();
        testMinusMonthsLongMinValue();
    }

    private static void testMinusMonthsZero() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.minusMonths(0L);
        if (!result.equals(ym)) {
            throw new AssertionError("minusMonths(0) should return the same YearMonth");
        }
    }

    private static void testMinusMonthsPositive() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth expected = YearMonth.of(2020, 3);
        YearMonth result = ym.minusMonths(2L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(2) from 2020-05 should be 2020-03 but was " + result);
        }
    }

    private static void testMinusMonthsNegative() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth expected = YearMonth.of(2020, 7);
        YearMonth result = ym.minusMonths(-2L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(-2) from 2020-05 should be 2020-07 but was " + result);
        }
    }

    private static void testMinusMonthsLongMinValue() {
        YearMonth ym = YearMonth.of(2020, 1);
        // According to the code, minusMonths(Long.MIN_VALUE) == plusMonths(Long.MAX_VALUE).plusMonths(1)
        YearMonth expected = ym.plusMonths(Long.MAX_VALUE).plusMonths(1);
        YearMonth result = ym.minusMonths(Long.MIN_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("minusMonths(Long.MIN_VALUE) did not match expected result");
        }
    }
}
