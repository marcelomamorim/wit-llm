/*
 * @test
 * @summary Test YearMonth.plusYears(long) method for normal and boundary cases
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
    }

    private static void testPlusYearsZero() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        YearMonth result = ym.plusYears(0);
        if (result != ym) {
            throw new AssertionError("plusYears(0) should return same instance");
        }
    }

    private static void testPlusYearsPositive() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        YearMonth result = ym.plusYears(5);
        if (result.getYear() != 2005 || result.getMonthValue() != 1) {
            throw new AssertionError("plusYears(5) did not add years correctly");
        }
    }

    private static void testPlusYearsNegative() {
        YearMonth ym = YearMonth.of(2000, Month.DECEMBER);
        YearMonth result = ym.plusYears(-10);
        if (result.getYear() != 1990 || result.getMonthValue() != 12) {
            throw new AssertionError("plusYears(-10) did not subtract years correctly");
        }
    }

    private static void testPlusYearsOverflow() {
        YearMonth ym = YearMonth.of(YearMonth.MIN_YEAR, Month.JANUARY);
        long yearsToAdd = (long) Integer.MIN_VALUE - 1L; // one less than Integer.MIN_VALUE to cause overflow
        try {
            ym.plusYears(yearsToAdd);
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException e) {
            // expected
        }

        ym = YearMonth.of(YearMonth.MAX_YEAR, Month.DECEMBER);
        yearsToAdd = (long) Integer.MAX_VALUE + 1L; // one more than Integer.MAX_VALUE to cause overflow
        try {
            ym.plusYears(yearsToAdd);
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
