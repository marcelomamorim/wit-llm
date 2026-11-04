/*
 * @test
 * @summary Test java.time.YearMonth.minusYears(long) method behavior
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testMinusYearsZero();
        testMinusYearsPositive();
        testMinusYearsNegative();
        testMinusYearsLongMinValue();
    }

    static void testMinusYearsZero() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth result = ym.minusYears(0L);
        if (!result.equals(ym)) {
            throw new AssertionError("minusYears(0) should return the same YearMonth");
        }
    }

    static void testMinusYearsPositive() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth expected = YearMonth.of(1995, 1);
        YearMonth result = ym.minusYears(5L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(5) should subtract 5 years");
        }
    }

    static void testMinusYearsNegative() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth expected = YearMonth.of(2005, 1);
        YearMonth result = ym.minusYears(-5L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(-5) should add 5 years");
        }
    }

    static void testMinusYearsLongMinValue() {
        YearMonth ym = YearMonth.of(2000, 1);
        // According to the method, minusYears(Long.MIN_VALUE) == plusYears(Long.MAX_VALUE).plusYears(1)
        YearMonth expected = ym.plusYears(Long.MAX_VALUE).plusYears(1);
        YearMonth result = ym.minusYears(Long.MIN_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(Long.MIN_VALUE) should equal plusYears(Long.MAX_VALUE).plusYears(1)");
        }
    }
}
