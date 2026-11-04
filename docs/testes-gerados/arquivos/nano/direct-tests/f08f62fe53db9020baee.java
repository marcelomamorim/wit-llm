/* @test    @summary Test Date.parse with valid ISO 8601 date string    @run main DateWitupTest */

import java.util.Date;

public class DateWitupTest {

    public static void main(String[] args) {
        testParseValidISO8601();
        testParseInvalidFormat();
        testParseNull();
        testParseEmptyString();
        testParseWithComments();
        testParseWithTimezoneOffset();
    }

    static void testParseValidISO8601() {
        String dateStr = "2023-03-15T12:30:45";
        long expected = 1678871445000L; // milliseconds since epoch for this date
        long result = Date.parse(dateStr);
        assert result == expected : "Expected " + expected + " but got " + result;
    }

    static void testParseInvalidFormat() {
        String invalidStr = "Invalid Date String";
        try {
            Date.parse(invalidStr);
            assert false : "Expected IllegalArgumentException for invalid format";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testParseNull() {
        try {
            Date.parse(null);
            assert false : "Expected NullPointerException for null input";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParseEmptyString() {
        try {
            Date.parse("");
            assert false : "Expected IllegalArgumentException for empty string";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testParseWithComments() {
        String dateStr = "(Comment) 2023-03-15";
        long expected = 1678838400000L; // milliseconds for 2023-03-15
        long result = Date.parse(dateStr);
        assert result == expected : "Expected " + expected + " but got " + result;
    }

    static void testParseWithTimezoneOffset() {
        String dateStr = "2023-03-15T12:30:45+0200";
        long expected = 1678871445000L - 2 * 60 * 60 * 1000; // adjust for timezone offset
        long result = Date.parse(dateStr);
        assert result == expected : "Expected " + expected + " but got " + result;
    }
}
