/*
 * @test
 * @summary Test DateTimeFormatterBuilder.toFormatter(Locale) for null locale and normal usage
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testToFormatterWithNullLocale();
        testToFormatterWithValidLocale();
    }

    private static void testToFormatterWithNullLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.toFormatter(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testToFormatterWithValidLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Locale locale = Locale.US;
        DateTimeFormatter formatter = builder.toFormatter(locale);
        if (formatter == null) {
            throw new AssertionError("toFormatter should not return null for valid locale");
        }
        // Check that the formatter's locale is the one passed
        if (!locale.equals(formatter.getLocale())) {
            throw new AssertionError("Formatter locale does not match the input locale");
        }
        // Check that the resolver style is SMART by default
        if (formatter.getResolverStyle() != ResolverStyle.SMART) {
            throw new AssertionError("Default resolver style should be SMART");
        }
    }
}
