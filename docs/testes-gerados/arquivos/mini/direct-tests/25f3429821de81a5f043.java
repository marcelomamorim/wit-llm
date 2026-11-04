/*
 * @test
 * @summary Test LocalDateTime.of(int,int,int,int,int,int,int) factory method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testInvalidDate();
        testInvalidTime();
    }

    private static void testValidDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 30, 23, 59, 59, 999999999);
        if (ldt.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (ldt.getMonthValue() != 9) throw new AssertionError("Month mismatch");
        if (ldt.getDayOfMonth() != 30) throw new AssertionError("Day mismatch");
        if (ldt.getHour() != 23) throw new AssertionError("Hour mismatch");
        if (ldt.getMinute() != 59) throw new AssertionError("Minute mismatch");
        if (ldt.getSecond() != 59) throw new AssertionError("Second mismatch");
        if (ldt.getNano() != 999999999) throw new AssertionError("Nano mismatch");
    }

    private static void testInvalidDate() {
        try {
            LocalDateTime.of(2018, 2, 29, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid date");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidTime() {
        try {
            LocalDateTime.of(2018, 1, 1, 24, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid hour");
        } catch (java.time.DateTimeException e) {
            // expected
        }

        try {
            LocalDateTime.of(2018, 1, 1, 0, 60, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid minute");
        } catch (java.time.DateTimeException e) {
            // expected
        }

        try {
            LocalDateTime.of(2018, 1, 1, 0, 0, 60, 0);
            throw new AssertionError("Expected DateTimeException for invalid second");
        } catch (java.time.DateTimeException e) {
            // expected
        }

        try {
            LocalDateTime.of(2018, 1, 1, 0, 0, 0, 1000000000);
            throw new AssertionError("Expected DateTimeException for invalid nano");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
