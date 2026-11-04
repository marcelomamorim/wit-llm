/*
 * @test
 * @summary Test java.time.format.DateTimeFormatter.ofPattern(String) for null input and valid pattern
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testOfPatternWithNull();
        testOfPatternWithValidPattern();
    }

    private static void testOfPatternWithNull() {
        try {
            DateTimeFormatter.ofPattern(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testOfPatternWithValidPattern() {
        // Use a simple valid pattern
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (formatter == null) {
            throw new AssertionError("Formatter should not be null for valid pattern");
        }
        // Test formatting a known date string to check basic functionality
        String formatted = formatter.format(java.time.LocalDate.of(2018, 9, 1));
        if (!"2018-09-01".equals(formatted)) {
            throw new AssertionError("Formatted date does not match expected output: " + formatted);
        }
    }
}
