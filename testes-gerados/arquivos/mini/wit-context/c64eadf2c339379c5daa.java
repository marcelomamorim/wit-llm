/*
 * @test
 * @summary Test OffsetDateTime.parse(CharSequence, DateTimeFormatter) for null inputs and normal parsing
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInputs();
    }

    private static void testParseWithNullFormatter() {
        try {
            OffsetDateTime.parse("2020-01-01T12:00:00+01:00", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            OffsetDateTime.parse(null, formatter);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithValidInputs() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String validText = "2020-01-01T12:00:00+01:00";
        OffsetDateTime odt = OffsetDateTime.parse(validText, formatter);
        if (!validText.equals(odt.toString())) {
            throw new AssertionError("Parsed OffsetDateTime string does not match input text");
        }

        // Test with invalid text to confirm DateTimeParseException is thrown
        String invalidText = "invalid-date-time";
        try {
            OffsetDateTime.parse(invalidText, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException e) {
            // Expected
        }
    }
}
