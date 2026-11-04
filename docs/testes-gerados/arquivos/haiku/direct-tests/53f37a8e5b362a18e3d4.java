/*
 * @test
 * @summary Verifies Year.atMonthDay(MonthDay) returns the LocalDate for the year and month-day, including leap-day handling and null rejection
 * @run main YearWitupTest
 */

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        testRegularDate();
        testLeapDayInLeapYear();
        testLeapDayInNonLeapYearFallsBackToFeb28();
        testExtremeYears();
        testNullMonthDayThrowsNPE();
    }

    static void testRegularDate() {
        LocalDate actual = Year.of(2019).atMonthDay(MonthDay.of(2, 28));
        check(LocalDate.of(2019, 2, 28).equals(actual), "2019-02-28 expected");
        LocalDate jan = Year.of(2019).atMonthDay(MonthDay.of(1, 1));
        check(LocalDate.of(2019, 1, 1).equals(jan), "2019-01-01 expected");
        LocalDate dec = Year.of(2019).atMonthDay(MonthDay.of(12, 31));
        check(LocalDate.of(2019, 12, 31).equals(dec), "2019-12-31 expected");
    }

    static void testLeapDayInLeapYear() {
        LocalDate actual = Year.of(2020).atMonthDay(MonthDay.of(2, 29));
        check(LocalDate.of(2020, 2, 29).equals(actual), "2020-02-29 expected");
    }

    static void testLeapDayInNonLeapYearFallsBackToFeb28() {
        LocalDate actual = Year.of(2019).atMonthDay(MonthDay.of(2, 29));
        check(LocalDate.of(2019, 2, 28).equals(actual), "2019 Feb 29 should resolve to 2019-02-28");
    }

    static void testExtremeYears() {
        LocalDate min = Year.of(Year.MIN_VALUE).atMonthDay(MonthDay.of(6, 15));
        check(min.getYear() == Year.MIN_VALUE, "min year mismatch");
        check(min.getMonthValue() == 6 && min.getDayOfMonth() == 15, "min year month-day mismatch");
        LocalDate max = Year.of(Year.MAX_VALUE).atMonthDay(MonthDay.of(6, 15));
        check(max.getYear() == Year.MAX_VALUE, "max year mismatch");
        check(max.getMonthValue() == 6 && max.getDayOfMonth() == 15, "max year month-day mismatch");
    }

    static void testNullMonthDayThrowsNPE() {
        boolean thrown = false;
        try {
            Year.of(2019).atMonthDay(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null MonthDay should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
