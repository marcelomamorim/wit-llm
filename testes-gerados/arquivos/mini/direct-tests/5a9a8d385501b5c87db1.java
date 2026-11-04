/*
 * @test
 * @summary Test java.time.ZonedDateTime.parse(CharSequence) with valid and invalid inputs
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseInvalid();
    }

    private static void testParseValid() {
        // Valid ISO_ZONED_DATE_TIME string
        String valid = "2011-12-03T10:15:30+01:00[Europe/Paris]";
        ZonedDateTime zdt = ZonedDateTime.parse(valid);
        if (!zdt.toString().equals(valid)) {
            throw new AssertionError("Parsed ZonedDateTime does not match input string");
        }

        // Another valid string with offset and zone
        String valid2 = "2007-12-03T10:15:30+01:00[Europe/Paris]";
        ZonedDateTime zdt2 = ZonedDateTime.parse(valid2);
        if (!zdt2.toString().equals(valid2)) {
            throw new AssertionError("Parsed ZonedDateTime does not match input string");
        }
    }

    private static void testParseInvalid() {
        // Null input should throw NullPointerException
        try {
            ZonedDateTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }

        // Empty string should throw DateTimeParseException
        try {
            ZonedDateTime.parse("");
            throw new AssertionError("Expected DateTimeParseException for empty string");
        } catch (DateTimeParseException expected) {
        }

        // Invalid format string
        try {
            ZonedDateTime.parse("invalid-date-time");
            throw new AssertionError("Expected DateTimeParseException for invalid format");
        } catch (DateTimeParseException expected) {
        }
    }
}
