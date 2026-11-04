/*
 * @test
 * @summary Test OffsetDateTime.parse(CharSequence) for null inputs and normal parsing
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testParseNullText();
        testParseValidText();
    }

    private static void testParseNullText() {
        // According to source, parse(text) calls parse(text, ISO_OFFSET_DATE_TIME)
        // Passing null text should throw NullPointerException
        try {
            OffsetDateTime.parse((CharSequence) null);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testParseValidText() {
        // Test a valid ISO_OFFSET_DATE_TIME string
        String valid = "2018-09-01T10:15:30+01:00";
        OffsetDateTime odt = OffsetDateTime.parse(valid);
        if (!valid.equals(odt.toString())) {
            throw new AssertionError("Parsed OffsetDateTime string mismatch: expected " + valid + " but got " + odt.toString());
        }

        // Test invalid format throws DateTimeParseException
        String invalid = "invalid-date-time";
        try {
            OffsetDateTime.parse(invalid);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }
}
