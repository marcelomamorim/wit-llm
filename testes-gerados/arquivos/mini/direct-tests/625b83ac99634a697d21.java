/*
 * @test
 * @summary Test java.lang.String.format(Locale, String, Object...) method
 * @run main StringWitupTest
 */

import java.util.Formatter;
import java.util.Locale;

public class StringWitupTest {
    public static void main(String[] args) {
        testFormatWithLocaleAndArgs();
        testFormatWithNullLocale();
        testFormatWithNullFormatString();
        testFormatWithNoArgs();
    }

    private static void testFormatWithLocaleAndArgs() {
        Locale locale = Locale.US;
        String format = "Hello %s %d";
        String result = String.format(locale, format, "World", 2021);
        if (!"Hello World 2021".equals(result)) {
            throw new AssertionError("Expected 'Hello World 2021' but got '" + result + "'");
        }
    }

    private static void testFormatWithNullLocale() {
        String format = "Value: %.2f";
        String result = String.format(null, format, 3.14159);
        if (!"Value: 3.14".equals(result)) {
            throw new AssertionError("Expected 'Value: 3.14' but got '" + result + "'");
        }
    }

    private static void testFormatWithNullFormatString() {
        try {
            String.format(Locale.US, null);
            throw new AssertionError("Expected NullPointerException for null format string");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testFormatWithNoArgs() {
        String format = "Static text without args";
        String result = String.format(Locale.US, format);
        if (!format.equals(result)) {
            throw new AssertionError("Expected '" + format + "' but got '" + result + "'");
        }
    }
}
