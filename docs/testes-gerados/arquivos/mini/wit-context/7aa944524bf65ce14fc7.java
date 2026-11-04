/*
 * @test
 * @summary Test java.time.Instant.parse(CharSequence) method
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testParseNull();
        testParseValidString();
        testParseInvalidString();
    }

    private static void testParseNull() {
        try {
            Instant.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testParseValidString() {
        // ISO_INSTANT format example
        String validInstant = "2018-09-01T10:15:30Z";
        Instant instant = Instant.parse(validInstant);
        if (!validInstant.equals(instant.toString())) {
            throw new AssertionError("Parsed Instant string mismatch: expected " + validInstant + " but got " + instant.toString());
        }
    }

    private static void testParseInvalidString() {
        String invalidInstant = "not-a-date";
        try {
            Instant.parse(invalidInstant);
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException expected) {
            // Expected exception
        }
    }
}
