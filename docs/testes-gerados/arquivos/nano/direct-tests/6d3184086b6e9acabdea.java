/*
 * @test
 * @summary Test MonthDay.atYear(int) method for various years.
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.LocalDate;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testAtYearWithValidYear();
        testAtYearWithInvalidYear();
        testAtYearWithBoundaryYears();
    }

    static void testAtYearWithValidYear() {
        MonthDay md = MonthDay.of(2, 29); // February 29
        LocalDate date = md.atYear(2020); // Leap year
        assert date.getYear() == 2020 : "Expected year 2020, got " + date.getYear();
        assert date.getMonthValue() == 2 : "Expected month 2, got " + date.getMonthValue();
        assert date.getDayOfMonth() == 29 : "Expected day 29, got " + date.getDayOfMonth();
    }

    static void testAtYearWithInvalidYear() {
        MonthDay md = MonthDay.of(2, 29); // February 29
        LocalDate date = md.atYear(2019); // Non-leap year, should fallback to 28
        assert date.getYear() == 2019 : "Expected year 2019, got " + date.getYear();
        assert date.getMonthValue() == 2 : "Expected month 2, got " + date.getMonthValue();
        assert date.getDayOfMonth() == 28 : "Expected day 28, got " + date.getDayOfMonth();
    }

    static void testAtYearWithBoundaryYears() {
        MonthDay md = MonthDay.of(1, 31); // January 31
        LocalDate date = md.atYear(0); // Year zero
        assert date.getYear() == 0 : "Expected year 0, got " + date.getYear();
        assert date.getMonthValue() == 1 : "Expected month 1, got " + date.getMonthValue();
        assert date.getDayOfMonth() == 31 : "Expected day 31, got " + date.getDayOfMonth();

        date = md.atYear(1); // Year one
        assert date.getYear() == 1 : "Expected year 1, got " + date.getYear();
        assert date.getMonthValue() == 1 : "Expected month 1, got " + date.getMonthValue();
        assert date.getDayOfMonth() == 31 : "Expected day 31, got " + date.getDayOfMonth();
    }
}
