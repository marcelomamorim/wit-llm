/*
 * @test
 * @summary Tests for java.time.Instant.parse(CharSequence)
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantWitupTest {

    public static void main(String[] args) {
        testParseWithNullInput();
        testParseWithValidInput();
        testParseWithInvalidInput();
    }

    private static void testParseWithNullInput() {
        try {
            Instant.parse(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null input");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null input: " + e);
        }
    }

    private static void testParseWithValidInput() {
        String validText = "2023-03-25T12:34:56.789Z";
        try {
            Instant result = Instant.parse(validText);
            if (result != null) {
                System.out.println("Passed: Valid input parsed successfully: " + result);
            } else {
                System.err.println("Failed: parse returned null for valid input");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception thrown for valid input: " + e);
        }
    }

    private static void testParseWithInvalidInput() {
        String invalidText = "not-a-valid-instant";
        try {
            Instant.parse(invalidText);
            System.err.println("Failed: Expected DateTimeParseException for invalid input");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Passed: DateTimeParseException thrown as expected for invalid input");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for invalid input: " + e);
        }
    }
}
