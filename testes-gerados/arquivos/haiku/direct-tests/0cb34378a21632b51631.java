/*
 * @test
 * @summary Behavioral tests for LocalDate.plusMonths(long)
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkDate(LocalDate actual, int year, int month, int day) {
        check(actual.getYear() == year && actual.getMonthValue() == month && actual.getDayOfMonth() == day,
              "expected " + year + "-" + month + "-" + day + " but was " + actual);
    }

    static void testZeroReturnsSameInstance() {
        LocalDate d = LocalDate.of(2020, 1, 31);
        check(d.plusMonths(0) == d, "plusMonths(0) must return this");
    }

    static void testSimpleAddition() {
        checkDate(LocalDate.of(2000, 1, 1).plusMonths(1), 2000, 2, 1);
        checkDate(LocalDate.of(2000, 1, 1).plusMonths(12), 2001, 1, 1);
    }

    static void testYearRollover() {
        checkDate(LocalDate.of(2020, 12, 15).plusMonths(1), 2021, 1, 15);
    }

    static void testNegativeMonths() {
        checkDate(LocalDate.of(2020, 1, 15).plusMonths(-1), 2019, 12, 15);
        checkDate(LocalDate.of(2020, 3, 15).plusMonths(-3), 2019, 12, 15);
    }

    static void testDayClampingLeapYear() {
        checkDate(LocalDate.of(2020, 1, 31).plusMonths(1), 2020, 2, 29);
        checkDate(LocalDate.of(2020, 3, 31).plusMonths(-1), 2020, 2, 29);
    }

    static void testDayClampingNonLeapYear() {
        checkDate(LocalDate.of(2021, 1, 31).plusMonths(1), 2021, 2, 28);
        checkDate(LocalDate.of(2019, 1, 31).plusMonths(1), 2019, 2, 28);
    }

    static void testDayKeptWhenValid() {
        checkDate(LocalDate.of(2020, 5, 10).plusMonths(7), 2020, 12, 10);
    }

    static void testMaxYearBoundaryThrows() {
        boolean thrown = false;
        try {
            LocalDate.of(999999999, 12, 1).plusMonths(1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeException when year exceeds maximum");
    }

    static void testMinYearBoundaryThrows() {
        boolean thrown = false;
        try {
            LocalDate.of(-999999999, 1, 1).plusMonths(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeException when year is below minimum");
    }

    public static void main(String[] args) {
        testZeroReturnsSameInstance();
        testSimpleAddition();
        testYearRollover();
        testNegativeMonths();
        testDayClampingLeapYear();
        testDayClampingNonLeapYear();
        testDayKeptWhenValid();
        testMaxYearBoundaryThrows();
        testMinYearBoundaryThrows();
    }
}
