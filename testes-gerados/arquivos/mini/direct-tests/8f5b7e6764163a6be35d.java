/*
 * @test
 * @summary Test java.time.LocalTime.parse(CharSequence) with valid and invalid inputs
 * @run main java.time.LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseInvalid();
    }

    private static void testParseValid() {
        // Test parsing a valid ISO_LOCAL_TIME string
        LocalTime t = LocalTime.parse("10:15:30");
        if (t.getHour() != 10 || t.getMinute() != 15 || t.getSecond() != 30) {
            throw new AssertionError("Parsed time does not match expected values");
        }

        // Test parsing a valid ISO_LOCAL_TIME string with nanoseconds
        LocalTime t2 = LocalTime.parse("23:59:59.999999999");
        if (t2.getHour() != 23 || t2.getMinute() != 59 || t2.getSecond() != 59 || t2.getNano() != 999999999) {
            throw new AssertionError("Parsed time with nanos does not match expected values");
        }

        // Test parsing a valid ISO_LOCAL_TIME string with only hours and minutes
        LocalTime t3 = LocalTime.parse("00:00");
        if (t3.getHour() != 0 || t3.getMinute() != 0 || t3.getSecond() != 0) {
            throw new AssertionError("Parsed time with only hours and minutes does not match expected values");
        }
    }

    private static void testParseInvalid() {
        // Test parsing an invalid time string - missing colon
        try {
            LocalTime.parse("101530");
            throw new AssertionError("Expected DateTimeParseException for invalid format");
        } catch (DateTimeParseException expected) {
            // expected
        }

        // Test parsing an invalid time string - invalid hour
        try {
            LocalTime.parse("25:00:00");
            throw new AssertionError("Expected DateTimeParseException for invalid hour");
        } catch (DateTimeParseException expected) {
            // expected
        }

        // Test parsing an invalid time string - invalid minute
        try {
            LocalTime.parse("10:60:00");
            throw new AssertionError("Expected DateTimeParseException for invalid minute");
        } catch (DateTimeParseException expected) {
            // expected
        }

        // Test parsing an invalid time string - invalid second
        try {
            LocalTime.parse("10:00:60");
            throw new AssertionError("Expected DateTimeParseException for invalid second");
        } catch (DateTimeParseException expected) {
            // expected
        }

        // Test parsing null input
        try {
            LocalTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
