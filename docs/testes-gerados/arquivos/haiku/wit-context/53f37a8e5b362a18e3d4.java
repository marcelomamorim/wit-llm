/*
 * @test
 * @summary Witup-derived checks for Year.atMonthDay(MonthDay)
 * @run main YearWitupTest
 */

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        testRegularDate();
        testLeapDayInLeapYear();
        testLeapDayInCommonYearIsClampedToFebruary28();
        testExtremeSupportedYear();
        testNullMonthDayThrowsNPE();
    }

    static void testRegularDate() {
        LocalDate actual = Year.of(2011).atMonthDay(MonthDay.of(12, 25));
        check(LocalDate.of(2011, 12, 25).equals(actual), "2011-12-25 expected but was " + actual);
    }

    static void testLeapDayInLeapYear() {
        LocalDate actual = Year.of(2012).atMonthDay(MonthDay.of(2, 29));
        check(LocalDate.of(2012, 2, 29).equals(actual), "2012-02-29 expected but was " + actual);
    }

    static void testLeapDayInCommonYearIsClampedToFebruary28() {
        LocalDate actual = Year.of(2011).atMonthDay(MonthDay.of(2, 29));
        check(LocalDate.of(2011, 2, 28).equals(actual), "2011-02-28 expected but was " + actual);
    }

    static void testExtremeSupportedYear() {
        LocalDate actual = Year.of(-999999999).atMonthDay(MonthDay.of(1, 1));
        check(LocalDate.of(-999999999, 1, 1).equals(actual), "min year date expected but was " + actual);
        LocalDate max = Year.of(999999999).atMonthDay(MonthDay.of(12, 31));
        check(LocalDate.of(999999999, 12, 31).equals(max), "max year date expected but was " + max);
    }

    static void testNullMonthDayThrowsNPE() {
        boolean thrown = false;
        try {
            Year.of(2011).atMonthDay(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "NullPointerException expected for null MonthDay");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
