/*
 * @test
 * @summary Test for YearMonth.minusYears(long)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testMinusYearsZero();
        testMinusYearsPositive();
        testMinusYearsNegative();
        testMinusYearsLongMin();
        testMinusYearsLongMax();
    }

    static void testMinusYearsZero() {
        YearMonth ym = YearMonth.of(2023, 3);
        YearMonth result = ym.minusYears(0);
        assert result.equals(ym) : "minusYears(0) should return the same YearMonth";
    }

    static void testMinusYearsPositive() {
        YearMonth ym = YearMonth.of(2023, 3);
        YearMonth result = ym.minusYears(5);
        YearMonth expected = YearMonth.of(2018, 3);
        assert result.equals(expected) : "minusYears(5) should subtract 5 years";
    }

    static void testMinusYearsNegative() {
        YearMonth ym = YearMonth.of(2023, 3);
        YearMonth result = ym.minusYears(-3);
        YearMonth expected = YearMonth.of(2026, 3);
        assert result.equals(expected) : "minusYears(-3) should add 3 years";
    }

    static void testMinusYearsLongMin() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth result = ym.minusYears(Long.MIN_VALUE);
        YearMonth expected = ym.plusYears(Long.MAX_VALUE).plusYears(1);
        assert result.equals(expected) : "minusYears(Long.MIN_VALUE) should be equivalent to plusYears(Long.MAX_VALUE).plusYears(1)";
    }

    static void testMinusYearsLongMax() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth result = ym.minusYears(Long.MAX_VALUE);
        YearMonth expected = ym.plusYears(-Long.MAX_VALUE);
        assert result.equals(expected) : "minusYears(Long.MAX_VALUE) should be equivalent to plusYears(-Long.MAX_VALUE)";
    }
}
