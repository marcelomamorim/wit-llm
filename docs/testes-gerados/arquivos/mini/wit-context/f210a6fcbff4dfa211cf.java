/*
 * @test
 * @summary Test java.util.Formatter.locale() method behavior including FormatterClosedException
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.util.FormatterClosedException;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testLocaleReturnsLocale();
        testLocaleThrowsFormatterClosedException();
    }

    private static void testLocaleReturnsLocale() {
        Formatter formatter = new Formatter();
        Locale locale = formatter.locale();
        if (locale == null) {
            throw new AssertionError("Expected non-null locale from Formatter.locale()");
        }
    }

    private static void testLocaleThrowsFormatterClosedException() {
        Formatter formatter = new Formatter();
        try {
            formatter.close();
            formatter.locale();
            throw new AssertionError("Expected FormatterClosedException when calling locale() on closed Formatter");
        } catch (FormatterClosedException expected) {
            // Expected exception
        }
    }
}
