/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.withHour(int): valid hour changes, unchanged date and time fields, and DateTimeException for out-of-range hours
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidHourChange();
        testSameHourReturnsEqualValue();
        testBoundaryHours();
        testInvalidHourHigh();
        testInvalidHourLow();
    }

    static void testValidHourChange() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30, 123456789);
        LocalDateTime result = dt.withHour(5);
        LocalDateTime expected = LocalDateTime.of(2007, 12, 3, 5, 15, 30, 123456789);
        check(expected.equals(result), "withHour(5) expected " + expected + " but was " + result);
        check(result.toLocalDate().equals(LocalDate.of(2007, 12, 3)), "date must be preserved");
        check(result.toLocalTime().equals(LocalTime.of(5, 15, 30, 123456789)), "time fields other than hour must be preserved");
    }

    static void testSameHourReturnsEqualValue() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30);
        LocalDateTime result = dt.withHour(10);
        check(dt.equals(result), "withHour with same hour must equal original");
    }

    static void testBoundaryHours() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30);
        check(dt.withHour(0).equals(LocalDateTime.of(2007, 12, 3, 0, 15, 30)), "hour 0 must be accepted");
        check(dt.withHour(23).equals(LocalDateTime.of(2007, 12, 3, 23, 15, 30)), "hour 23 must be accepted");
    }

    static void testInvalidHourHigh() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30);
        boolean thrown = false;
        try {
            dt.withHour(24);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "withHour(24) must throw DateTimeException");
    }

    static void testInvalidHourLow() {
        LocalDateTime dt = LocalDateTime.of(2007, 12, 3, 10, 15, 30);
        boolean thrown = false;
        try {
            dt.withHour(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "withHour(-1) must throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
