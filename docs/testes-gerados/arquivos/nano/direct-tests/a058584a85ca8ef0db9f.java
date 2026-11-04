/*
 * @test
 * @summary Test DateTimeFormatter.ofPattern(String, Locale) method for various patterns and locales.
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testOfPatternWithValidPatternAndLocale();
        testOfPatternWithNullPattern();
        testOfPatternWithNullLocale();
        testOfPatternWithEmptyPattern();
        testOfPatternWithInvalidPattern();
    }

    static void testOfPatternWithValidPatternAndLocale() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
            if (dtf == null) {
                throw new AssertionError("Returned formatter is null for valid pattern and locale");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid pattern and locale: " + e);
        }
    }

    static void testOfPatternWithNullPattern() {
        try {
            DateTimeFormatter.ofPattern(null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null pattern: " + e);
        }
    }

    static void testOfPatternWithNullLocale() {
        try {
            DateTimeFormatter.ofPattern("yyyy-MM-dd", null);
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null locale: " + e);
        }
    }

    static void testOfPatternWithEmptyPattern() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("", Locale.US);
            if (dtf == null) {
                throw new AssertionError("Returned formatter is null for empty pattern");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty pattern: " + e);
        }
    }

    static void testOfPatternWithInvalidPattern() {
        try {
            DateTimeFormatter.ofPattern("invalid[pattern", Locale.US);
            // No exception expected, as pattern may be accepted but may cause runtime errors during formatting
        } catch (IllegalArgumentException e) {
            // expected if pattern is invalid
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid pattern: " + e);
        }
    }
}
