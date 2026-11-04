/*
 * @test
 * @summary Test OffsetDateTime.parse(CharSequence) with valid and invalid inputs
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseInvalid();
    }

    private static void testParseValid() {
        // Valid ISO_OFFSET_DATE_TIME string
        String valid = "2011-12-03T10:15:30+01:00";
        OffsetDateTime odt = OffsetDateTime.parse(valid);
        if (!odt.toString().startsWith("2011-12-03T10:15:30+01:00")) {
            throw new AssertionError("Parsed OffsetDateTime does not match input");
        }

        // Valid with fractional seconds
        String validFraction = "2011-12-03T10:15:30.123456789+01:00";
        OffsetDateTime odtFraction = OffsetDateTime.parse(validFraction);
        if (!odtFraction.toString().startsWith("2011-12-03T10:15:30.123456789+01:00")) {
            throw new AssertionError("Parsed OffsetDateTime with fraction does not match input");
        }
    }

    private static void testParseInvalid() {
        // Null input should throw NullPointerException
        try {
            OffsetDateTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }

        // Invalid format string should throw DateTimeParseException
        String invalid = "not-a-date-time";
        try {
            OffsetDateTime.parse(invalid);
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException expected) {
        }

        // Missing offset should throw DateTimeParseException
        String missingOffset = "2011-12-03T10:15:30";
        try {
            OffsetDateTime.parse(missingOffset);
            throw new AssertionError("Expected DateTimeParseException for missing offset");
        } catch (DateTimeParseException expected) {
        }
    }
}
