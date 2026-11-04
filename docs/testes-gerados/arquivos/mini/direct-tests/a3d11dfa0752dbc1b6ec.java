/*
 * @test
 * @summary Test YearMonth.plusYears(long) method behavior
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
    }

    private static void testPlusYearsZero() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth result = ym.plusYears(0);
        if (result != ym) {
            throw new AssertionError("plusYears(0) should return same instance");
        }
    }

    private static void testPlusYearsPositive() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth result = ym.plusYears(5);
        if (result.getYear() != 2005 || result.getMonthValue() != 1) {
            throw new AssertionError("plusYears(5) did not add years correctly");
        }
    }

    private static void testPlusYearsNegative() {
        YearMonth ym = YearMonth.of(2000, 1);
        YearMonth result = ym.plusYears(-10);
        if (result.getYear() != 1990 || result.getMonthValue() != 1) {
            throw new AssertionError("plusYears(-10) did not subtract years correctly");
        }
    }

    private static void testPlusYearsOverflow() {
        YearMonth ym = YearMonth.of(YearMonth.MIN_YEAR, 1);
        try {
            ym.plusYears(-1L);
            throw new AssertionError("plusYears with overflow should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
        ym = YearMonth.of(YearMonth.MAX_YEAR, 1);
        try {
            ym.plusYears(1L);
            throw new AssertionError("plusYears with overflow should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
