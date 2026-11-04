/*
 * @test
 * @summary Test DateTimeFormatter.ofLocalizedTime(FormatStyle) method
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.chrono.IsoChronology;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testOfLocalizedTimeWithValidStyles();
        testOfLocalizedTimeWithNull();
    }

    private static void testOfLocalizedTimeWithValidStyles() {
        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(style);
            // Check formatter is not null
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for style: " + style);
            }
            // Check formatter resolver style is SMART
            if (formatter.getResolverStyle() != ResolverStyle.SMART) {
                throw new AssertionError("ResolverStyle should be SMART for style: " + style);
            }
            // Check formatter chronology is IsoChronology
            if (!Objects.equals(formatter.getChronology(), IsoChronology.INSTANCE)) {
                throw new AssertionError("Chronology should be IsoChronology for style: " + style);
            }
        }
    }

    private static void testOfLocalizedTimeWithNull() {
        try {
            DateTimeFormatter.ofLocalizedTime(null);
            throw new AssertionError("Expected NullPointerException for null timeStyle");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
