/*
 * @test
 * @summary Test LocalDateTime.of(int,int,int,int,int) for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testInvalidYear();
        testInvalidMonth();
        testInvalidDayOfMonth();
    }

    private static void testValidDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2018, 12, 31, 23, 59);
        if (ldt.getYear() != 2018 || ldt.getMonthValue() != 12 || ldt.getDayOfMonth() != 31
                || ldt.getHour() != 23 || ldt.getMinute() != 59) {
            throw new AssertionError("Valid LocalDateTime fields do not match input");
        }
    }

    private static void testInvalidYear() {
        try {
            LocalDateTime.of(-1, 1, 1, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid year -1");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDateTime.of(10000, 1, 1, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid year 10000");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMonth() {
        try {
            LocalDateTime.of(2018, 0, 1, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid month 0");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDateTime.of(2018, 13, 1, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid month 13");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidDayOfMonth() {
        try {
            LocalDateTime.of(2018, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth 0");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDateTime.of(2018, 1, 32, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth 32");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDateTime.of(2018, 2, 29, 0, 0); // 2018 is not leap year
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth 29 in Feb 2018");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
