/*
 * @test
 * @summary Tests YearMonth.plusYears for zero, positive, negative, leap-month and out-of-range year results
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        YearMonth ym = YearMonth.of(2007, 12);

        check(ym.plusYears(0) == ym, "plusYears(0) must return the same instance");
        check(ym.plusYears(1).equals(YearMonth.of(2008, 12)), "2007-12 + 1 year must be 2008-12");
        check(ym.plusYears(-7).equals(YearMonth.of(2000, 12)), "2007-12 - 7 years must be 2000-12");
        check(YearMonth.of(2000, 1).plusYears(-1).equals(YearMonth.of(1999, 1)), "2000-01 - 1 year must be 1999-01");
        check(YearMonth.of(2000, Month.FEBRUARY).plusYears(1).equals(YearMonth.of(2001, 2)),
              "2000-02 + 1 year must be 2001-02");
        check(YearMonth.of(2000, 6).plusYears(100).getMonth() == Month.JUNE,
              "month must be preserved by plusYears");

        expectDateTimeException(YearMonth.of(999999999, 12), 1L);
        expectDateTimeException(YearMonth.of(-999999999, 1), -1L);
        expectDateTimeException(YearMonth.of(2007, 12), Long.MAX_VALUE);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void expectDateTimeException(YearMonth ym, long years) {
        try {
            YearMonth result = ym.plusYears(years);
            failures++;
            System.err.println("Expected DateTimeException for " + ym + " plusYears(" + years + ") but got " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }
}
