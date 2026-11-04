/*
 * @test
 * @summary Test DateTimeFormatter.ofLocalizedDate(FormatStyle) method
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.chrono.IsoChronology;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testOfLocalizedDateWithValidStyles();
        testOfLocalizedDateWithNullStyle();
    }

    private static void testOfLocalizedDateWithValidStyles() {
        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(style);
            // Check formatter is not null
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for style: " + style);
            }
            // Check resolver style is SMART
            if (formatter.getResolverStyle() != ResolverStyle.SMART) {
                throw new AssertionError("ResolverStyle should be SMART for style: " + style);
            }
            // Check chronology is IsoChronology
            if (!Objects.equals(formatter.getChronology(), IsoChronology.INSTANCE)) {
                throw new AssertionError("Chronology should be IsoChronology for style: " + style);
            }
        }
    }

    private static void testOfLocalizedDateWithNullStyle() {
        try {
            DateTimeFormatter.ofLocalizedDate(null);
            throw new AssertionError("Expected NullPointerException for null dateStyle");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
