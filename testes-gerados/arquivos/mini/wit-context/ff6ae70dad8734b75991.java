/*
 * @test
 * @summary Deterministic tests for java.time.Year.parse(CharSequence, DateTimeFormatter)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class YearWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInput();
        testParseWithInvalidInput();
    }

    private static void testParseWithNullFormatter() {
        try {
            Year.parse("2020", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        try {
            Year.parse(null, formatter);
            throw new AssertionError("Expected DateTimeParseException for null text");
        } catch (DateTimeParseException e) {
            // Expected
        } catch (NullPointerException e) {
            // Defensive: if implementation throws NPE instead of DTPEx
            // Acceptable fallback
        }
    }

    private static void testParseWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        Year y = Year.parse("2020", formatter);
        if (y.getValue() != 2020) {
            throw new AssertionError("Parsed year value mismatch: expected 2020 but got " + y.getValue());
        }
    }

    private static void testParseWithInvalidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        try {
            Year.parse("invalid", formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException e) {
            // Expected
        }
    }
}
