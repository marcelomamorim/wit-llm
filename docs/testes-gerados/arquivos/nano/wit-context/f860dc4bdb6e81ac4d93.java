/*
 * @test
 * @summary Tests for java.time.YearMonth.minusMonths(long)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testMinusMonthsNormal();
        testMinusMonthsLongMinValue();
        testMinusMonthsLargePositive();
        testMinusMonthsLargeNegative();
    }

    private static void testMinusMonthsNormal() {
        YearMonth ym = YearMonth.of(2023, 3);
        YearMonth result = ym.minusMonths(2);
        if (!result.equals(YearMonth.of(2023, 1))) {
            throw new AssertionError("Expected 2023-01, got " + result);
        }
    }

    private static void testMinusMonthsLongMinValue() {
        YearMonth ym = YearMonth.of(2023, 3);
        YearMonth result = ym.minusMonths(Long.MIN_VALUE);
        YearMonth expected = ym.plusMonths(Long.MAX_VALUE).plusMonths(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Long.MIN_VALUE case failed: expected " + expected + ", got " + result);
        }
    }

    private static void testMinusMonthsLargePositive() {
        YearMonth ym = YearMonth.of(2023, 3);
        long largePositive = 1000000L;
        YearMonth result = ym.minusMonths(largePositive);
        YearMonth expected = ym.plusMonths(-largePositive);
        if (!result.equals(expected)) {
            throw new AssertionError("Large positive months case failed: expected " + expected + ", got " + result);
        }
    }

    private static void testMinusMonthsLargeNegative() {
        YearMonth ym = YearMonth.of(2023, 3);
        long largeNegative = -1000000L;
        YearMonth result = ym.minusMonths(largeNegative);
        YearMonth expected = ym.plusMonths(-largeNegative);
        if (!result.equals(expected)) {
            throw new AssertionError("Large negative months case failed: expected " + expected + ", got " + result);
        }
    }
}
