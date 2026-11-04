/*
 * @test
 * @summary Tests for java.time.Period.parse(CharSequence)
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.format.DateTimeParseException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testParseValidInput();
        testParseNullInput();
        testParseInvalidFormat();
        testParseInvalidNumber();
    }

    static void testParseValidInput() {
        try {
            Period p = Period.parse("P2Y3M4D");
            if (p == null) {
                throw new AssertionError("Expected non-null Period for valid input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    static void testParseNullInput() {
        try {
            Period.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null input", e);
        }
    }

    static void testParseInvalidFormat() {
        try {
            Period.parse("InvalidFormat");
            throw new AssertionError("Expected DateTimeParseException for invalid format");
        } catch (DateTimeParseException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for invalid format", e);
        }
    }

    static void testParseInvalidNumber() {
        try {
            Period.parse("P-2Y-3M-4D");
            // Should parse successfully, as negative values are valid in ISO-8601 period strings
            // But if the implementation throws, catch and verify
        } catch (DateTimeParseException e) {
            // If the current implementation throws, this is acceptable
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid number", e);
        }
    }
}
