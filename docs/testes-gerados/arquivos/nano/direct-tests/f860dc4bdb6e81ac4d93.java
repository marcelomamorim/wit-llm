/*
 * @test
 * @summary Test for YearMonth.minusMonths(long)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import static java.time.Month.JANUARY;
import static java.time.Month.DECEMBER;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testMinusMonthsZero();
        testMinusMonthsPositive();
        testMinusMonthsNegative();
        testMinusMonthsLongMin();
    }

    static void testMinusMonthsZero() {
        YearMonth ym = YearMonth.of(2023, JANUARY);
        YearMonth result = ym.minusMonths(0);
        assert result.equals(ym) : "minusMonths(0) should return the same YearMonth";
    }

    static void testMinusMonthsPositive() {
        YearMonth ym = YearMonth.of(2023, JANUARY);
        YearMonth result = ym.minusMonths(3);
        YearMonth expected = YearMonth.of(2022, OCTOBER);
        assert result.equals(expected) : "minusMonths(3) from Jan 2023 should be Oct 2022";
    }

    static void testMinusMonthsNegative() {
        YearMonth ym = YearMonth.of(2023, JANUARY);
        YearMonth result = ym.minusMonths(-2);
        YearMonth expected = YearMonth.of(2023, MARCH);
        assert result.equals(expected) : "minusMonths(-2) from Jan 2023 should be Mar 2023";
    }

    static void testMinusMonthsLongMin() {
        YearMonth ym = YearMonth.of(2023, JANUARY);
        YearMonth result = ym.minusMonths(Long.MIN_VALUE);
        // Long.MIN_VALUE is -9223372036854775808
        // The method computes: plusMonths(Long.MAX_VALUE).plusMonths(1)
        // Long.MAX_VALUE is 9223372036854775807
        YearMonth expected = ym.plusMonths(Long.MAX_VALUE).plusMonths(1);
        assert result.equals(expected) : "minusMonths(Long.MIN_VALUE) should equal plusMonths(Long.MAX_VALUE).plusMonths(1)";
    }
}
