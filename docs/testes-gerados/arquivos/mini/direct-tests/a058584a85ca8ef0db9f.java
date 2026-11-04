/*
 * @test
 * @summary Test DateTimeFormatter.ofPattern(String, Locale) basic behavior
 * @run main java.time.format.DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testValidPatternAndLocale();
        testValidPatternNullLocale();
        testEmptyPattern();
        testInvalidPattern();
    }

    private static void testValidPatternAndLocale() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        if (dtf == null) {
            throw new AssertionError("DateTimeFormatter.ofPattern returned null for valid pattern and locale");
        }
    }

    private static void testValidPatternNullLocale() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy", null);
        if (dtf == null) {
            throw new AssertionError("DateTimeFormatter.ofPattern returned null for valid pattern and null locale");
        }
    }

    private static void testEmptyPattern() {
        try {
            DateTimeFormatter.ofPattern("", Locale.US);
            throw new AssertionError("Expected IllegalArgumentException for empty pattern");
        } catch (IllegalArgumentException | NullPointerException e) {
            // Expected: IllegalArgumentException or NullPointerException depending on implementation
        }
    }

    private static void testInvalidPattern() {
        try {
            DateTimeFormatter.ofPattern("invalid_pattern", Locale.US);
            throw new AssertionError("Expected IllegalArgumentException for invalid pattern");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
