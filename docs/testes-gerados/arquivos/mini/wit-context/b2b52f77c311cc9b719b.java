/*
 * @test
 * @summary Test java.time.LocalDate.getDayOfWeek() method behavior
 * @run main LocalDateWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testGetDayOfWeekKnownDates();
        testGetDayOfWeekConsistency();
    }

    private static void testGetDayOfWeekKnownDates() {
        // Test known date: 2018-09-24 is Monday
        LocalDate date = LocalDate.of(2018, Month.SEPTEMBER, 24);
        DayOfWeek dow = date.getDayOfWeek();
        if (dow != DayOfWeek.MONDAY) {
            throw new AssertionError("Expected MONDAY but got " + dow);
        }

        // Test known date: 2000-01-01 is Saturday
        date = LocalDate.of(2000, 1, 1);
        dow = date.getDayOfWeek();
        if (dow != DayOfWeek.SATURDAY) {
            throw new AssertionError("Expected SATURDAY but got " + dow);
        }

        // Test known date: 1970-01-01 is Thursday
        date = LocalDate.of(1970, 1, 1);
        dow = date.getDayOfWeek();
        if (dow != DayOfWeek.THURSDAY) {
            throw new AssertionError("Expected THURSDAY but got " + dow);
        }
    }

    private static void testGetDayOfWeekConsistency() {
        // Test that getDayOfWeek returns a valid DayOfWeek enum for a range of dates
        // from 1970-01-01 to 1970-01-07 (one week)
        LocalDate start = LocalDate.of(1970, 1, 1);
        DayOfWeek[] expected = {
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY
        };
        for (int i = 0; i < expected.length; i++) {
            LocalDate date = start.plusDays(i);
            DayOfWeek dow = date.getDayOfWeek();
            if (dow != expected[i]) {
                throw new AssertionError("Date " + date + " expected " + expected[i] + " but got " + dow);
            }
        }
    }
}
