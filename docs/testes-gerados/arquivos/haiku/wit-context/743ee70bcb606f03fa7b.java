/*
 * @test
 * @summary Witup-derived checks for LocalDate.of(int, Month, int) exception and boundary behavior
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        validDates();
        yearBoundaries();
        invalidYearThrows();
        nullMonthThrows();
        invalidDayOfMonthThrows();
        dayInvalidForMonthThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void validDates() {
        LocalDate d = LocalDate.of(2020, Month.FEBRUARY, 29);
        check(d.getYear() == 2020, "year mismatch");
        check(d.getMonth() == Month.FEBRUARY, "month mismatch");
        check(d.getDayOfMonth() == 29, "day mismatch");

        LocalDate first = LocalDate.of(2019, Month.JANUARY, 1);
        check(first.getYear() == 2019, "year mismatch first");
        check(first.getMonth() == Month.JANUARY, "month mismatch first");
        check(first.getDayOfMonth() == 1, "day mismatch first");
    }

    static void yearBoundaries() {
        LocalDate min = LocalDate.of(-999999999, Month.JANUARY, 1);
        check(min.getYear() == -999999999, "min year mismatch");

        LocalDate max = LocalDate.of(999999999, Month.DECEMBER, 31);
        check(max.getYear() == 999999999, "max year mismatch");
        check(max.getDayOfMonth() == 31, "max day mismatch");
    }

    static void invalidYearThrows() {
        expectDateTime(1000000000, Month.JANUARY, 1, "year above max");
        expectDateTime(-1000000000, Month.JANUARY, 1, "year below min");
    }

    static void nullMonthThrows() {
        try {
            LocalDate.of(2020, null, 1);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null month");
    }

    static void invalidDayOfMonthThrows() {
        expectDateTime(2020, Month.JANUARY, 0, "day zero");
        expectDateTime(2020, Month.JANUARY, 32, "day 32");
        expectDateTime(2020, Month.JANUARY, -1, "negative day");
    }

    static void dayInvalidForMonthThrows() {
        expectDateTime(2019, Month.FEBRUARY, 29, "Feb 29 non-leap");
        expectDateTime(2020, Month.APRIL, 31, "Apr 31");
    }

    static void expectDateTime(int year, Month month, int day, String label) {
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException: " + label);
    }
}
