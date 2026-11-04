/*
 * @test
 * @summary WITUP-derived behavioral tests for LocalDateTime.of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond)
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testInvalidMonth();
        testInvalidDayOfMonth();
        testLeapDay();
        testInvalidTimeFields();
        testInvalidNanoOfSecond();
        testInvalidYearRange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectDateTimeException(Runnable action, String message) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException('expected DateTimeException: ' + message);
    }

    static void testValidValues() {
        LocalDateTime dt = LocalDateTime.of(2018, 9, 15, 10, 30, 45, 123);
        check(dt.getYear() == 2018, 'year');
        check(dt.getMonthValue() == 9, 'month');
        check(dt.getDayOfMonth() == 15, 'day');
        check(dt.getHour() == 10, 'hour');
        check(dt.getMinute() == 30, 'minute');
        check(dt.getSecond() == 45, 'second');
        check(dt.getNano() == 123, 'nano');

        LocalDateTime max = LocalDateTime.of(2018, 12, 31, 23, 59, 59, 999999999);
        check(max.getMonthValue() == 12, 'max month');
        check(max.getDayOfMonth() == 31, 'max day');
        check(max.getHour() == 23, 'max hour');
        check(max.getMinute() == 59, 'max minute');
        check(max.getSecond() == 59, 'max second');
        check(max.getNano() == 999999999, 'max nano');

        LocalDateTime min = LocalDateTime.of(2018, 1, 1, 0, 0, 0, 0);
        check(min.getMonthValue() == 1, 'min month');
        check(min.getDayOfMonth() == 1, 'min day');
        check(min.getHour() == 0, 'min hour');
        check(min.getNano() == 0, 'min nano');
    }

    static void testInvalidMonth() {
        expectDateTimeException(() -> LocalDateTime.of(2018, 0, 1, 0, 0, 0, 0), 'month 0');
        expectDateTimeException(() -> LocalDateTime.of(2018, 13, 1, 0, 0, 0, 0), 'month 13');
    }

    static void testInvalidDayOfMonth() {
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 0, 0, 0, 0, 0), 'day 0');
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 31, 0, 0, 0, 0), 'day 31 in September');
        expectDateTimeException(() -> LocalDateTime.of(2018, 4, 31, 0, 0, 0, 0), 'day 31 in April');
        expectDateTimeException(() -> LocalDateTime.of(2018, 1, 32, 0, 0, 0, 0), 'day 32 in January');
    }

    static void testLeapDay() {
        LocalDateTime leap = LocalDateTime.of(2000, 2, 29, 0, 0, 0, 0);
        check(leap.getDayOfMonth() == 29, 'leap day 2000');
        check(leap.getMonthValue() == 2, 'leap month 2000');

        expectDateTimeException(() -> LocalDateTime.of(2018, 2, 29, 0, 0, 0, 0), 'Feb 29 in non-leap 2018');
        expectDateTimeException(() -> LocalDateTime.of(1900, 2, 29, 0, 0, 0, 0), 'Feb 29 in non-leap 1900');
    }

    static void testInvalidTimeFields() {
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 15, 24, 0, 0, 0), 'hour 24');
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 15, -1, 0, 0, 0), 'hour -1');
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 15, 0, 60, 0, 0), 'minute 60');
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 15, 0, 0, 60, 0), 'second 60');
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 15, 0, -1, 0, 0), 'minute -1');
    }

    static void testInvalidNanoOfSecond() {
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 15, 0, 0, 0, 1000000000), 'nano 1e9');
        expectDateTimeException(() -> LocalDateTime.of(2018, 9, 15, 0, 0, 0, -1), 'nano -1');
    }

    static void testInvalidYearRange() {
        expectDateTimeException(() -> LocalDateTime.of(1000000000, 1, 1, 0, 0, 0, 0), 'year above MAX_YEAR');
        expectDateTimeException(() -> LocalDateTime.of(-1000000000, 1, 1, 0, 0, 0, 0), 'year below MIN_YEAR');
    }
}
