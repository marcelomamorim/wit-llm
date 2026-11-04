/*
 * @test
 * @summary Witup-derived checks for YearMonth.minusYears(long), including the Long.MIN_VALUE branch and out-of-range results
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testRegularSubtraction();
        testZeroSubtraction();
        testNegativeSubtractionAddsYears();
        testMinValueThrowsDateTimeException();
        testResultOutOfRangeThrowsDateTimeException();
    }

    static void testRegularSubtraction() {
        YearMonth ym = YearMonth.of(2000, 5).minusYears(3);
        check(ym.getYear() == 1997, "expected year 1997 but was " + ym.getYear());
        check(ym.getMonthValue() == 5, "expected month 5 but was " + ym.getMonthValue());
    }

    static void testZeroSubtraction() {
        YearMonth base = YearMonth.of(2010, 12);
        YearMonth ym = base.minusYears(0);
        check(ym.equals(base), "minusYears(0) should return an equal value");
    }

    static void testNegativeSubtractionAddsYears() {
        YearMonth ym = YearMonth.of(2000, 1).minusYears(-2);
        check(ym.getYear() == 2002, "expected year 2002 but was " + ym.getYear());
        check(ym.getMonthValue() == 1, "expected month 1 but was " + ym.getMonthValue());
    }

    static void testMinValueThrowsDateTimeException() {
        boolean thrown = false;
        try {
            YearMonth.of(2000, 5).minusYears(Long.MIN_VALUE);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "minusYears(Long.MIN_VALUE) should throw DateTimeException");
    }

    static void testResultOutOfRangeThrowsDateTimeException() {
        boolean thrown = false;
        try {
            YearMonth.of(999999999, 12).minusYears(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "result beyond the supported year range should throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
