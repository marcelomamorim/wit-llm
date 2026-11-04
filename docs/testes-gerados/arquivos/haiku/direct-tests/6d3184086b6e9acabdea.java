/*
 * @test
 * @summary Checks MonthDay.atYear(int) for leap and non-leap years and for non-February dates
 * @run main MonthDayWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(message + ": expected " + expected + " but got " + actual);
        }
    }

    static void testFebruary29InLeapYear() {
        MonthDay md = MonthDay.of(Month.FEBRUARY, 29);
        checkEquals(LocalDate.of(2000, 2, 29), md.atYear(2000), "leap year 2000");
        checkEquals(LocalDate.of(2024, 2, 29), md.atYear(2024), "leap year 2024");
    }

    static void testFebruary29InNonLeapYearFallsBackTo28() {
        MonthDay md = MonthDay.of(Month.FEBRUARY, 29);
        checkEquals(LocalDate.of(2001, 2, 28), md.atYear(2001), "non-leap year 2001");
        checkEquals(LocalDate.of(1900, 2, 28), md.atYear(1900), "non-leap year 1900");
    }

    static void testNonFebruaryDateIgnoresLeapness() {
        MonthDay md = MonthDay.of(12, 25);
        checkEquals(LocalDate.of(2020, 12, 25), md.atYear(2020), "December 25 in 2020");
        checkEquals(LocalDate.of(2021, 12, 25), md.atYear(2021), "December 25 in 2021");
    }

    static void testResultKeepsMonthAndDay() {
        MonthDay md = MonthDay.of(6, 15);
        LocalDate result = md.atYear(1999);
        checkEquals(Month.JUNE, result.getMonth(), "month preserved");
        check(result.getDayOfMonth() == 15, "day preserved");
        check(result.getYear() == 1999, "year preserved");
    }

    public static void main(String[] args) {
        testFebruary29InLeapYear();
        testFebruary29InNonLeapYearFallsBackTo28();
        testNonFebruaryDateIgnoresLeapness();
        testResultKeepsMonthAndDay();
    }
}
