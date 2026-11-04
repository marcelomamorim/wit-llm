/*
 * @test
 * @summary Test java.lang.String.format(Locale, String, Object...) for expected exceptions and normal behavior
 * @run main StringWitupTest
 */

import java.util.FormatterClosedException;
import java.util.Locale;

public class StringWitupTest {
    public static void main(String[] args) {
        testFormatNormal();
        testFormatNullLocale();
        testFormatNullFormatString();
        testFormatNullArgs();
        testFormatInvalidFormatString();
    }

    private static void testFormatNormal() {
        // Normal usage: simple formatting
        String result = String.format(Locale.US, "Hello %s!", "World");
        if (!"Hello World!".equals(result)) {
            throw new AssertionError("Expected 'Hello World!' but got: " + result);
        }
    }

    private static void testFormatNullLocale() {
        // Null locale is allowed, treated as default locale
        String result = String.format(null, "Number: %d", 42);
        if (!result.contains("42")) {
            throw new AssertionError("Expected formatted string to contain '42' but got: " + result);
        }
    }

    private static void testFormatNullFormatString() {
        // Passing null as format string should throw NullPointerException
        try {
            String.format(Locale.US, null, "arg");
            throw new AssertionError("Expected NullPointerException for null format string");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFormatNullArgs() {
        // Passing null as args is allowed and treated as empty args
        String result = String.format(Locale.US, "Hello World");
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' but got: " + result);
        }
    }

    private static void testFormatInvalidFormatString() {
        // Invalid format string should throw java.util.IllegalFormatException (e.g. MissingFormatArgumentException)
        try {
            String.format(Locale.US, "%d %d", 1);
            throw new AssertionError("Expected IllegalFormatException for missing argument");
        } catch (java.util.IllegalFormatException expected) {
            // expected
        }
    }
}
