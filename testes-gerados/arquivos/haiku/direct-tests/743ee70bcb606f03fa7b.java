/*
 * @test
 * @summary LocalDate.of(int, Month, int) returns expected dates and rejects invalid arguments
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidDates();
        testYearBounds();
        testNullMonth();
        testInvalidDayOfMonthRange();
        testInvalidDateForMonth();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidDates() {
        LocalDate d = LocalDate.of(2018, Month.MARCH, 15);
        check(d.getYear() == 2018, "year");
        check(d.getMonthValue() == 3, "month");
        check(d.getDayOfMonth() == 15, "day");
        check(d.equals(LocalDate.of(2018, 3, 15)), "equals to int-month factory");

        LocalDate leap = LocalDate.of(2000, Month.FEBRUARY, 29);
        check(leap.getMonthValue() == 2 && leap.getDayOfMonth() == 29, "leap day 2000");

        LocalDate first = LocalDate.of(1999, Month.DECEMBER, 1);
        check(first.getMonthValue() == 12 && first.getDayOfMonth() == 1, "first day");
    }

    static void testYearBounds() {
        LocalDate min = LocalDate.of(-999999999, Month.JANUARY, 1);
        check(min.getYear() == -999999999, "min year");

        LocalDate max = LocalDate.of(999999999, Month.DECEMBER, 31);
        check(max.getYear() == 999999999, "max year");

        boolean thrown = false;
        try {
            LocalDate.of(1000000000, Month.JANUARY, 1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "year above range must throw DateTimeException");
    }

    static void testNullMonth() {
        boolean thrown = false;
        try {
            LocalDate.of(2018, (Month) null, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null month must throw NullPointerException");
    }

    static void testInvalidDayOfMonthRange() {
        boolean zeroThrown = false;
        try {
            LocalDate.of(2018, Month.JANUARY, 0);
        } catch (DateTimeException e) {
            zeroThrown = true;
        }
        check(zeroThrown, "day 0 must throw DateTimeException");

        boolean thirtyTwoThrown = false;
        try {
            LocalDate.of(2018, Month.JANUARY, 32);
        } catch (DateTimeException e) {
            thirtyTwoThrown = true;
        }
        check(thirtyTwoThrown, "day 32 must throw DateTimeException");
    }

    static void testInvalidDateForMonth() {
        boolean feb29Thrown = false;
        try {
            LocalDate.of(2018, Month.FEBRUARY, 29);
        } catch (DateTimeException e) {
            feb29Thrown = true;
        }
        check(feb29Thrown, "Feb 29 in non-leap year must throw DateTimeException");

        boolean apr31Thrown = false;
        try {
            LocalDate.of(2018, Month.APRIL, 31);
        } catch (DateTimeException e) {
            apr31Thrown = true;
        }
        check(apr31Thrown, "April 31 must throw DateTimeException");
    }
}
