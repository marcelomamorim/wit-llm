/*
 * @test
 * @summary Behavioral tests for LocalDate.getDayOfWeek() using public API only
 * @run main LocalDateWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;

public class LocalDateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkDay(LocalDate date, DayOfWeek expected) {
        DayOfWeek actual = date.getDayOfWeek();
        check(actual == expected,
              "Expected " + expected + " for " + date + " but got " + actual);
    }

    static void testKnownDates() {
        checkDay(LocalDate.of(1970, 1, 1), DayOfWeek.THURSDAY);
        checkDay(LocalDate.of(1969, 12, 31), DayOfWeek.WEDNESDAY);
        checkDay(LocalDate.of(2000, 1, 1), DayOfWeek.SATURDAY);
        checkDay(LocalDate.of(2024, 1, 1), DayOfWeek.MONDAY);
        checkDay(LocalDate.of(2024, 2, 29), DayOfWeek.THURSDAY);
    }

    static void testConsecutiveDaysCycleThroughWeek() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        DayOfWeek[] order = {
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
        };
        for (int i = 0; i < 366; i++) {
            checkDay(start.plusDays(i), order[i % 7]);
        }
    }

    static void testSameWeekdayAfterSevenDays() {
        LocalDate date = LocalDate.of(1999, 12, 31);
        check(date.getDayOfWeek() == date.plusDays(7).getDayOfWeek(),
              "Weekday must repeat after 7 days");
        check(date.getDayOfWeek() == date.minusDays(14).getDayOfWeek(),
              "Weekday must repeat after 14 days backwards");
    }

    static void testMinimumSupportedDateIsConsistent() {
        LocalDate min = LocalDate.MIN;
        DayOfWeek dow = min.getDayOfWeek();
        check(dow != null, "getDayOfWeek must not return null for LocalDate.MIN");
        check(min.plusDays(7).getDayOfWeek() == dow,
              "Weekday must repeat after 7 days near LocalDate.MIN");
    }

    public static void main(String[] args) {
        testKnownDates();
        testConsecutiveDaysCycleThroughWeek();
        testSameWeekdayAfterSevenDays();
        testMinimumSupportedDateIsConsistent();
    }
}
