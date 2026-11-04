/*
 * @test
 * @summary Test DateTimeFormatterBuilder.toFormatter(Locale) method
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testToFormatterWithNullLocale();
        testToFormatterWithValidLocale();
    }

    private static void testToFormatterWithNullLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Passing null locale should not throw and should return a non-null formatter
        DateTimeFormatter formatter = builder.toFormatter(null);
        if (formatter == null) {
            throw new AssertionError("toFormatter(null) returned null");
        }
    }

    private static void testToFormatterWithValidLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Locale locale = Locale.US;
        DateTimeFormatter formatter = builder.toFormatter(locale);
        if (formatter == null) {
            throw new AssertionError("toFormatter(Locale.US) returned null");
        }
    }
}
