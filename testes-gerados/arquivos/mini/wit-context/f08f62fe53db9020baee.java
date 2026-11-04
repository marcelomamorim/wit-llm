/*
 * @test
 * @summary Test java.util.Date.parse(String) for valid and invalid inputs
 * @run main java.util.DateWitupTest
 */

import java.util.Date;

public class DateWitupTest {
    public static void main(String[] args) {
        testValidDateParsing();
        testInvalidDateParsing();
    }

    private static void testValidDateParsing() {
        // Test a valid date string with full date and time
        String validDate = "Thu, 01 Jan 1970 00:00:00 GMT";
        long time = Date.parse(validDate);
        // The epoch time for 1970-01-01T00:00:00Z is 0L
        if (time != 0L) {
            throw new RuntimeException("Expected epoch time 0L but got " + time);
        }

        // Test a valid date string with two-digit year < 70 (should be adjusted)
        String validDate2 = "01 Jan 69 00:00:00 GMT";
        long time2 = Date.parse(validDate2);
        if (time2 >= 0L) {
            throw new RuntimeException("Expected negative time for year 1969 but got " + time2);
        }

        // Test a valid date string with timezone offset
        String validDate3 = "01 Jan 1970 01:00:00 +0100";
        long time3 = Date.parse(validDate3);
        // The time3 should be 0L because +0100 means one hour east of GMT (offset applied)
        if (time3 != 0L) {
            throw new RuntimeException("Expected epoch time 0L for timezone offset but got " + time3);
        }
    }

    private static void testInvalidDateParsing() {
        // Test null input - should throw IllegalArgumentException
        try {
            Date.parse(null);
            throw new RuntimeException("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test empty string - should throw IllegalArgumentException
        try {
            Date.parse("");
            throw new RuntimeException("Expected IllegalArgumentException for empty string");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test malformed string - invalid format
        try {
            Date.parse("invalid-date-string");
            throw new RuntimeException("Expected IllegalArgumentException for malformed string");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test string with invalid time (hour > 23)
        try {
            Date.parse("01 Jan 1970 25:00:00 GMT");
            throw new RuntimeException("Expected IllegalArgumentException for invalid hour");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test string with invalid month (nonexistent month string)
        try {
            Date.parse("01 Xxx 1970 00:00:00 GMT");
            throw new RuntimeException("Expected IllegalArgumentException for invalid month string");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
