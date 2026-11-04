/*
 * @test
 * @summary Tests for java.time.ZonedDateTime.of(LocalDateTime, ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidYearBelowRange();
        testInvalidYearAboveRange();
        testInvalidMonthBelowRange();
        testInvalidMonthAboveRange();
        testInvalidDayBelowRange();
        testInvalidDayAboveRange();
    }

    static void testValidInputs() {
        LocalDateTime ldt = LocalDateTime.of(2020, 5, 15, 10, 30, 45, 123456789);
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zone);
        if (zdt == null) {
            throw new AssertionError("Expected non-null ZonedDateTime for valid inputs");
        }
    }

    static void testInvalidYearBelowRange() {
        try {
            LocalDateTime.of(0, 5, 15, 10, 30, 45, 123456789);
            throw new AssertionError("Expected DateTimeException for year below valid range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidYearAboveRange() {
        try {
            LocalDateTime.of(10000, 5, 15, 10, 30, 45, 123456789);
            throw new AssertionError("Expected DateTimeException for year above valid range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMonthBelowRange() {
        try {
            LocalDateTime.of(2020, 0, 15, 10, 30, 45, 123456789);
            throw new AssertionError("Expected DateTimeException for month below valid range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMonthAboveRange() {
        try {
            LocalDateTime.of(2020, 13, 15, 10, 30, 45, 123456789);
            throw new AssertionError("Expected DateTimeException for month above valid range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDayBelowRange() {
        try {
            LocalDateTime.of(2020, 5, 0, 10, 30, 45, 123456789);
            throw new AssertionError("Expected DateTimeException for day below valid range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDayAboveRange() {
        try {
            LocalDateTime.of(2020, 5, 32, 10, 30, 45, 123456789);
            throw new AssertionError("Expected DateTimeException for day above valid range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
