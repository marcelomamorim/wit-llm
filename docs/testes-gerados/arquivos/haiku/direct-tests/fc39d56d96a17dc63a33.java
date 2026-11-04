/*
 * @test
 * @summary Tests LocalDate.withDayOfYear for identity, day changes, leap-year handling and invalid day-of-year values
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testSameDayOfYearReturnsThis();
        testChangesDayOfYearKeepingYear();
        testLeapYearDay366();
        testNonLeapYearDay366Throws();
        testOutOfRangeThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectDateTimeException(LocalDate date, int dayOfYear) {
        try {
            date.withDayOfYear(dayOfYear);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for day of year " + dayOfYear);
    }

    static void testSameDayOfYearReturnsThis() {
        LocalDate date = LocalDate.of(2007, 3, 15);
        int current = date.getDayOfYear();
        LocalDate result = date.withDayOfYear(current);
        check(result == date, "withDayOfYear with same day of year must return this");
    }

    static void testChangesDayOfYearKeepingYear() {
        LocalDate first = LocalDate.of(2007, 12, 31).withDayOfYear(1);
        check(first.equals(LocalDate.of(2007, 1, 1)), "expected 2007-01-01 but got " + first);

        LocalDate mid = LocalDate.of(2007, 1, 1).withDayOfYear(100);
        check(mid.equals(LocalDate.of(2007, 4, 10)), "expected 2007-04-10 but got " + mid);
        check(mid.getYear() == 2007, "year must be preserved");
    }

    static void testLeapYearDay366() {
        LocalDate result = LocalDate.of(2008, 2, 29).withDayOfYear(366);
        check(result.equals(LocalDate.of(2008, 12, 31)), "expected 2008-12-31 but got " + result);
    }

    static void testNonLeapYearDay366Throws() {
        expectDateTimeException(LocalDate.of(2007, 6, 1), 366);
    }

    static void testOutOfRangeThrows() {
        LocalDate date = LocalDate.of(2008, 6, 1);
        expectDateTimeException(date, 0);
        expectDateTimeException(date, 367);
        expectDateTimeException(date, -1);
    }
}
