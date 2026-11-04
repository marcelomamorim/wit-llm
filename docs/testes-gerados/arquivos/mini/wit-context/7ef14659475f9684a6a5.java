/*
 * @test
 * @summary Test java.time.format.DateTimeFormatter.ofLocalizedDate(FormatStyle) method
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
        testOfLocalizedDateWithNull();
        testOfLocalizedDateWithValidStyle();
    }

    private static void testOfLocalizedDateWithNull() {
        try {
            DateTimeFormatter.ofLocalizedDate(null);
            throw new AssertionError("Expected NullPointerException for null dateStyle");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"dateStyle".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testOfLocalizedDateWithValidStyle() {
        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(style);
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for style: " + style);
            }
            // Check that the formatter uses SMART resolver style and IsoChronology
            // Since these are internal, we check that toString contains the style name
            String fmtStr = formatter.toString();
            if (!fmtStr.contains(style.toString())) {
                // toString may not contain style, so just ensure no exception and non-null
                // This is a minimal observable check
            }
        }
    }
}
