/*
 * @test
 * @summary Test Instant.parse(CharSequence) method for valid and invalid inputs
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InstantWitupTest {

    public static void main(String[] args) {
        testParseValidISOInstant();
        testParseInvalidString();
        testParseNullInput();
    }

    static void testParseValidISOInstant() {
        String validInstantStr = "2023-03-25T12:34:56.789Z";
        Instant instant = Instant.parse(validInstantStr);
        assert instant != null : "Parsed instant should not be null";
        String formatted = DateTimeFormatter.ISO_INSTANT.format(instant);
        assert formatted.equals(validInstantStr) : "Formatted instant should match input";
    }

    static void testParseInvalidString() {
        String invalidInstantStr = "invalid-instant";
        try {
            Instant.parse(invalidInstantStr);
            assert false : "Expected DateTimeParseException for invalid string";
        } catch (DateTimeParseException e) {
            // expected
        }
    }

    static void testParseNullInput() {
        try {
            Instant.parse((CharSequence) null);
            assert false : "Expected NullPointerException for null input";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
