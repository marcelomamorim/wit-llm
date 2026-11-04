/*
 * @test
 * @summary Deterministic tests for java.time.YearMonth.minusYears(long)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testMinusYearsNormal();
        testMinusYearsLongMinValue();
    }

    private static void testMinusYearsNormal() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        YearMonth result = ym.minusYears(10);
        if (result.getYear() != 1990 || result.getMonthValue() != 1) {
            throw new AssertionError("minusYears normal case failed: " + result);
        }
        // Test subtracting zero years returns same YearMonth
        YearMonth same = ym.minusYears(0);
        if (!same.equals(ym)) {
            throw new AssertionError("minusYears zero years should return same instance");
        }
        // Test subtracting negative years is equivalent to plusYears
        YearMonth plus = ym.minusYears(-5);
        YearMonth expectedPlus = ym.plusYears(5);
        if (!plus.equals(expectedPlus)) {
            throw new AssertionError("minusYears negative years failed: " + plus);
        }
    }

    private static void testMinusYearsLongMinValue() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        // According to source, minusYears(Long.MIN_VALUE) calls plusYears(Long.MAX_VALUE).plusYears(1)
        YearMonth result = ym.minusYears(Long.MIN_VALUE);
        YearMonth expected = ym.plusYears(Long.MAX_VALUE).plusYears(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(Long.MIN_VALUE) did not match expected plusYears chain");
        }
    }
}
