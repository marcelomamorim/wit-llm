/*
 * @test
 * @summary Test java.time.Instant.parse(CharSequence) with valid and invalid inputs
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testParseValidInstant();
        testParseInvalidInstant();
    }

    private static void testParseValidInstant() {
        // Test parsing a valid ISO-8601 instant string
        String validInstant = "2018-09-01T12:34:56Z";
        Instant instant = Instant.parse(validInstant);
        if (!instant.toString().equals(validInstant)) {
            throw new AssertionError("Parsed instant does not match input string");
        }

        // Test parsing with fractional seconds
        String fractional = "2018-09-01T12:34:56.123456789Z";
        Instant fractionalInstant = Instant.parse(fractional);
        if (!fractionalInstant.toString().equals(fractional)) {
            throw new AssertionError("Parsed instant with fractional seconds does not match input string");
        }
    }

    private static void testParseInvalidInstant() {
        // Test parsing an invalid string - missing 'T'
        String invalid1 = "2018-09-01 12:34:56Z";
        try {
            Instant.parse(invalid1);
            throw new AssertionError("Expected DateTimeParseException for invalid input missing 'T'");
        } catch (DateTimeParseException e) {
            // Expected
        }

        // Test parsing an invalid string - invalid date
        String invalid2 = "2018-13-01T12:34:56Z";
        try {
            Instant.parse(invalid2);
            throw new AssertionError("Expected DateTimeParseException for invalid month");
        } catch (DateTimeParseException e) {
            // Expected
        }

        // Test parsing an empty string
        String empty = "";
        try {
            Instant.parse(empty);
            throw new AssertionError("Expected DateTimeParseException for empty string");
        } catch (DateTimeParseException e) {
            // Expected
        }

        // Test parsing a null input (should throw NullPointerException)
        try {
            Instant.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
