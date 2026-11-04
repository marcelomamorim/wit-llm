/*
 * @test
 * @summary Test DateTimeFormatter.ofPattern(String, Locale) for null and empty inputs
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testNullPattern();
        testNullLocaleWithEmptyPattern();
        testValidPatternAndLocale();
    }

    private static void testNullPattern() {
        try {
            DateTimeFormatter.ofPattern(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testNullLocaleWithEmptyPattern() {
        // According to WIT, if locale is null and pattern length <= 0 and pattern != null, NPE expected
        // Here pattern is empty string "" (length 0), locale null
        try {
            DateTimeFormatter.ofPattern("", null);
            throw new RuntimeException("Expected NullPointerException for null locale with empty pattern");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidPatternAndLocale() {
        // Basic sanity test: valid pattern and locale should not throw
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        if (dtf == null) {
            throw new RuntimeException("DateTimeFormatter.ofPattern returned null for valid inputs");
        }
    }
}
