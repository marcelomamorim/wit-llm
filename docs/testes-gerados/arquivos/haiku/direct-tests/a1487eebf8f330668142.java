/*
 * @test
 * @summary Witup-style tests for LocalDateTime.of(int, Month, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testRegularValues();
        testLeapDay();
        testBoundaryValues();
        testMatchesDateAndTimeFactory();
        testInvalidDayOfMonth();
        testInvalidHour();
        testInvalidMinute();
        testInvalidSecond();
        testInvalidYear();
    }

    static void testRegularValues() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 20, 30);
        check("2018-01-01T10:20:30".equals(dt.toString()), "regular value: " + dt);
        check(dt.getYear() == 2018, "year");
        check(dt.getMonth() == Month.JANUARY, "month");
        check(dt.getDayOfMonth() == 1, "day");
        check(dt.getHour() == 10, "hour");
        check(dt.getMinute() == 20, "minute");
        check(dt.getSecond() == 30, "second");
        check(dt.getNano() == 0, "nano");
    }

    static void testLeapDay() {
        LocalDateTime dt = LocalDateTime.of(2000, Month.FEBRUARY, 29, 0, 0, 0);
        check("2000-02-29T00:00".equals(dt.toString()), "leap day: " + dt);
    }

    static void testBoundaryValues() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59, 59);
        check("2018-12-31T23:59:59".equals(dt.toString()), "end of year: " + dt);
        LocalDateTime start = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        check("2018-01-01T00:00".equals(start.toString()), "start of year: " + start);
    }

    static void testMatchesDateAndTimeFactory() {
        LocalDateTime a = LocalDateTime.of(2018, Month.MARCH, 15, 12, 30, 45);
        LocalDateTime b = LocalDateTime.of(LocalDate.of(2018, 3, 15), LocalTime.of(12, 30, 45));
        check(a.equals(b), "must equal of(LocalDate, LocalTime)");
    }

    static void testInvalidDayOfMonth() {
        expectDateTimeException(() -> LocalDateTime.of(1900, Month.FEBRUARY, 29, 0, 0, 0), "non-leap Feb 29");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.APRIL, 31, 0, 0, 0), "April 31");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 0, 0, 0, 0), "day 0");
    }

    static void testInvalidHour() {
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, 24, 0, 0), "hour 24");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, -1, 0, 0), "hour -1");
    }

    static void testInvalidMinute() {
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, 0, 60, 0), "minute 60");
    }

    static void testInvalidSecond() {
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 60), "second 60");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, -1), "second -1");
    }

    static void testInvalidYear() {
        expectDateTimeException(() -> LocalDateTime.of(1000000000, Month.JANUARY, 1, 0, 0, 0), "year out of range");
    }

    interface Action {
        void run();
    }

    static void expectDateTimeException(Action action, String label) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for " + label);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
