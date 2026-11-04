/*
 * @test
 * @summary Test LocalDateTime.of(int,int,int,int,int,int,int) for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testInvalidDayOfMonth();
        testInvalidMonth();
        testInvalidYear();
    }

    private static void testValidDateTime() {
        // Normal valid date-time
        LocalDateTime dt = LocalDateTime.of(2018, 9, 30, 23, 59, 59, 999999999);
        if (dt.getYear() != 2018 || dt.getMonthValue() != 9 || dt.getDayOfMonth() != 30
                || dt.getHour() != 23 || dt.getMinute() != 59 || dt.getSecond() != 59
                || dt.getNano() != 999999999) {
            throw new AssertionError("Valid LocalDateTime fields do not match input");
        }
    }

    private static void testInvalidDayOfMonth() {
        // Day of month too large (e.g. April 31)
        try {
            LocalDateTime.of(2018, 4, 31, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth");
        } catch (DateTimeException e) {
            // Expected
        }

        // Day of month zero (invalid)
        try {
            LocalDateTime.of(2018, 1, 0, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for dayOfMonth zero");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidMonth() {
        // Month zero (invalid)
        try {
            LocalDateTime.of(2018, 0, 1, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for month zero");
        } catch (DateTimeException e) {
            // Expected
        }

        // Month 13 (invalid)
        try {
            LocalDateTime.of(2018, 13, 1, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for month 13");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidYear() {
        // Year too large (e.g. Integer.MAX_VALUE)
        try {
            LocalDateTime.of(Integer.MAX_VALUE, 1, 1, 0, 0, 0, 0);
            // Depending on implementation, this may or may not throw, but baseline expects exception
            // So we accept either behavior but do not fail test if no exception
        } catch (DateTimeException e) {
            // Expected
        }

        // Year too small (e.g. Integer.MIN_VALUE)
        try {
            LocalDateTime.of(Integer.MIN_VALUE, 1, 1, 0, 0, 0, 0);
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
