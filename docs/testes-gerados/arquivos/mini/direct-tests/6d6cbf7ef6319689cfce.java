/*
 * @test
 * @summary Test java.time.LocalDate.plusYears(long) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
        testPlusYearsLeapDayAdjust();
    }

    private static void testPlusYearsZero() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 1);
        LocalDate result = date.plusYears(0);
        if (result != date) {
            throw new AssertionError("plusYears(0) should return same instance");
        }
    }

    private static void testPlusYearsPositive() {
        LocalDate date = LocalDate.of(2018, Month.MARCH, 1);
        LocalDate result = date.plusYears(2);
        if (result.getYear() != 2020 || result.getMonth() != Month.MARCH || result.getDayOfMonth() != 1) {
            throw new AssertionError("plusYears(2) did not produce expected date");
        }
    }

    private static void testPlusYearsNegative() {
        LocalDate date = LocalDate.of(2018, Month.MARCH, 1);
        LocalDate result = date.plusYears(-3);
        if (result.getYear() != 2015 || result.getMonth() != Month.MARCH || result.getDayOfMonth() != 1) {
            throw new AssertionError("plusYears(-3) did not produce expected date");
        }
    }

    private static void testPlusYearsOverflow() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 1);
        try {
            // This should throw DateTimeException because year overflows int range
            date.plusYears((long) Integer.MAX_VALUE + 1);
            throw new AssertionError("Expected DateTimeException for year overflow");
        } catch (DateTimeException expected) {
            // expected
        }
        try {
            date.plusYears((long) Integer.MIN_VALUE - 1);
            throw new AssertionError("Expected DateTimeException for year underflow");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testPlusYearsLeapDayAdjust() {
        LocalDate leapDay = LocalDate.of(2016, Month.FEBRUARY, 29);
        LocalDate result = leapDay.plusYears(1);
        // 2017 is not a leap year, so day should be adjusted to Feb 28
        if (result.getYear() != 2017 || result.getMonth() != Month.FEBRUARY || result.getDayOfMonth() != 28) {
            throw new AssertionError("plusYears(1) from leap day should adjust to Feb 28");
        }
    }
}
