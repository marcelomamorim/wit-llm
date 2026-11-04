/*
 * @test
 * @summary Test LocalDateTime.of(int,int,int,int,int) factory method
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
    }

    private static void testValidDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 15, 13, 45);
        if (ldt.getYear() != 2018) throw new RuntimeException("Year mismatch");
        if (ldt.getMonthValue() != 9) throw new RuntimeException("Month mismatch");
        if (ldt.getDayOfMonth() != 15) throw new RuntimeException("Day mismatch");
        if (ldt.getHour() != 13) throw new RuntimeException("Hour mismatch");
        if (ldt.getMinute() != 45) throw new RuntimeException("Minute mismatch");
        if (ldt.getSecond() != 0) throw new RuntimeException("Second should be zero");
        if (ldt.getNano() != 0) throw new RuntimeException("Nano should be zero");
    }

    private static void testInvalidMonth() {
        try {
            LocalDateTime.of(2018, 13, 1, 0, 0);
            throw new RuntimeException("Expected DateTimeException for invalid month");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidDay() {
        try {
            LocalDateTime.of(2018, 2, 30, 0, 0);
            throw new RuntimeException("Expected DateTimeException for invalid day");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidHour() {
        try {
            LocalDateTime.of(2018, 1, 1, 24, 0);
            throw new RuntimeException("Expected DateTimeException for invalid hour");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMinute() {
        try {
            LocalDateTime.of(2018, 1, 1, 0, 60);
            throw new RuntimeException("Expected DateTimeException for invalid minute");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
