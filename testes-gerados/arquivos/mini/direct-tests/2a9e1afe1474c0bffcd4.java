/*
 * @test
 * @summary Test LocalDateTime.of(int,int,int,int,int,int) factory method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testInvalidMonth();
        testInvalidDay();
        testInvalidHour();
        testInvalidMinute();
        testInvalidSecond();
    }

    private static void testValidDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 30, 23, 59, 59);
        if (ldt.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (ldt.getMonthValue() != 9) throw new AssertionError("Month mismatch");
        if (ldt.getDayOfMonth() != 30) throw new AssertionError("Day mismatch");
        if (ldt.getHour() != 23) throw new AssertionError("Hour mismatch");
        if (ldt.getMinute() != 59) throw new AssertionError("Minute mismatch");
        if (ldt.getSecond() != 59) throw new AssertionError("Second mismatch");
    }

    private static void testInvalidMonth() {
        try {
            LocalDateTime.of(2018, 13, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidDay() {
        try {
            LocalDateTime.of(2018, 2, 30, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid day");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidHour() {
        try {
            LocalDateTime.of(2018, 1, 1, 24, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid hour");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMinute() {
        try {
            LocalDateTime.of(2018, 1, 1, 0, 60, 0);
            throw new AssertionError("Expected DateTimeException for invalid minute");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidSecond() {
        try {
            LocalDateTime.of(2018, 1, 1, 0, 0, 60);
            throw new AssertionError("Expected DateTimeException for invalid second");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
