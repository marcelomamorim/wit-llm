/*
 * @test
 * @summary Test DateTimeFormatterBuilder.toFormatter(Locale locale) method for correct behavior.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testToFormatterWithLocale();
        testToFormatterWithNullLocale();
    }

    static void testToFormatterWithLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            DateTimeFormatter formatter = builder.toFormatter(Locale.US);
            if (formatter == null) {
                throw new AssertionError("toFormatter(Locale) returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testToFormatterWithLocale: " + e);
        }
    }

    static void testToFormatterWithNullLocale() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            DateTimeFormatter formatter = builder.toFormatter(null);
            if (formatter == null) {
                throw new AssertionError("toFormatter(null) returned null");
            }
        } catch (NullPointerException e) {
            // Expected if null is not allowed
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testToFormatterWithNullLocale: " + e);
        }
    }
}
