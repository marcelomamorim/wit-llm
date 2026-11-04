/*
 * @test
 * @summary Test DateTimeFormatter.ofLocalizedTime(FormatStyle) for null argument and normal usage
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.chrono.IsoChronology;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testNullArgument();
        testValidFormatStyle();
    }

    private static void testNullArgument() {
        try {
            DateTimeFormatter.ofLocalizedTime(null);
            throw new AssertionError("Expected NullPointerException for null timeStyle");
        } catch (NullPointerException ex) {
            // Expected exception
            if (!"timeStyle".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", ex);
            }
        }
    }

    private static void testValidFormatStyle() {
        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(style);
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for style: " + style);
            }
            // Check that the formatter uses SMART resolver style and IsoChronology
            // We cannot access these directly, but toFormatter returns a DateTimeFormatter
            // that should format a LocalTime without exception
            try {
                // Format a fixed time to string to check basic functionality
                String result = formatter.format(java.time.LocalTime.of(12, 34, 56));
                if (result == null || result.isEmpty()) {
                    throw new AssertionError("Formatted string is null or empty for style: " + style);
                }
            } catch (Exception e) {
                throw new AssertionError("Formatting failed for style: " + style, e);
            }
        }
    }
}
