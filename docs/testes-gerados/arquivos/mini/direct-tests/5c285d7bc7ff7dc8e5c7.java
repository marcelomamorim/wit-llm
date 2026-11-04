/*
 * @test
 * @summary Test DateTimeFormatter.ofLocalizedDateTime(FormatStyle) method
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.chrono.IsoChronology;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testOfLocalizedDateTimeWithValidStyle();
        testOfLocalizedDateTimeWithNullStyle();
    }

    private static void testOfLocalizedDateTimeWithValidStyle() {
        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(style);
            Objects.requireNonNull(formatter);
            // The formatter should use SMART resolver style and IsoChronology
            if (formatter.getResolverStyle() != ResolverStyle.SMART) {
                throw new AssertionError("Expected ResolverStyle.SMART but got " + formatter.getResolverStyle());
            }
            if (!IsoChronology.INSTANCE.equals(formatter.getChronology())) {
                throw new AssertionError("Expected IsoChronology.INSTANCE but got " + formatter.getChronology());
            }
        }
    }

    private static void testOfLocalizedDateTimeWithNullStyle() {
        try {
            DateTimeFormatter.ofLocalizedDateTime(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
