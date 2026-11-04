/*
 * @test
 * @summary Test java.time.LocalDate.getDayOfWeek() method
 * @run main LocalDateWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testKnownDates();
        testEpochDayZero();
        testNegativeEpochDay();
    }

    private static void testKnownDates() {
        // 2018-09-01 was a Saturday
        LocalDate date = LocalDate.of(2018, 9, 1);
        DayOfWeek dow = date.getDayOfWeek();
        if (dow != DayOfWeek.SATURDAY) {
            throw new AssertionError("Expected SATURDAY but got " + dow);
        }

        // 2000-01-01 was a Saturday
        date = LocalDate.of(2000, 1, 1);
        dow = date.getDayOfWeek();
        if (dow != DayOfWeek.SATURDAY) {
            throw new AssertionError("Expected SATURDAY but got " + dow);
        }

        // 1970-01-01 was a Thursday
        date = LocalDate.of(1970, 1, 1);
        dow = date.getDayOfWeek();
        if (dow != DayOfWeek.THURSDAY) {
            throw new AssertionError("Expected THURSDAY but got " + dow);
        }
    }

    private static void testEpochDayZero() {
        // Epoch day 0 corresponds to 1970-01-01, Thursday
        LocalDate epochDate = LocalDate.of(1970, 1, 1);
        DayOfWeek dow = epochDate.getDayOfWeek();
        if (dow != DayOfWeek.THURSDAY) {
            throw new AssertionError("Expected THURSDAY for epoch day 0 but got " + dow);
        }
    }

    private static void testNegativeEpochDay() {
        // Date before epoch: 1969-12-31 was Wednesday
        LocalDate date = LocalDate.of(1969, 12, 31);
        DayOfWeek dow = date.getDayOfWeek();
        if (dow != DayOfWeek.WEDNESDAY) {
            throw new AssertionError("Expected WEDNESDAY but got " + dow);
        }

        // Date far before epoch: 1900-01-01 was Monday
        date = LocalDate.of(1900, 1, 1);
        dow = date.getDayOfWeek();
        if (dow != DayOfWeek.MONDAY) {
            throw new AssertionError("Expected MONDAY but got " + dow);
        }
    }
}
