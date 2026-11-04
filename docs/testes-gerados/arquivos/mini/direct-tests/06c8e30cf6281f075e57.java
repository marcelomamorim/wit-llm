/*
 * @test
 * @summary Test YearMonth.withYear(int) method for valid and boundary year values
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testWithYearValid();
        testWithYearInvalid();
    }

    private static void testWithYearValid() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        YearMonth result = ym.withYear(1999);
        if (result.getYear() != 1999 || result.getMonth() != Month.JANUARY) {
            throw new AssertionError("withYear did not set year correctly");
        }
        // Test boundary year values
        YearMonth ymMin = ym.withYear(YearMonth.MIN_YEAR);
        if (ymMin.getYear() != YearMonth.MIN_YEAR) {
            throw new AssertionError("withYear failed at MIN_YEAR");
        }
        YearMonth ymMax = ym.withYear(YearMonth.MAX_YEAR);
        if (ymMax.getYear() != YearMonth.MAX_YEAR) {
            throw new AssertionError("withYear failed at MAX_YEAR");
        }
    }

    private static void testWithYearInvalid() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        try {
            ym.withYear(YearMonth.MIN_YEAR - 1);
            throw new AssertionError("Expected DateTimeException for year below MIN_YEAR");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            ym.withYear(YearMonth.MAX_YEAR + 1);
            throw new AssertionError("Expected DateTimeException for year above MAX_YEAR");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
