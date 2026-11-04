/*
 * @test
 * @summary Verifies ofLocalizedTime rejects null FormatStyle and returns ISO/SMART formatters for every style
 * @run main DateTimeFormatterWitupTest
 */

import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        boolean npeThrown = false;
        try {
            DateTimeFormatter.ofLocalizedTime(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null timeStyle");
        }

        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter f = DateTimeFormatter.ofLocalizedTime(style);
            if (f == null) {
                throw new RuntimeException("Formatter must not be null for style " + style);
            }
            if (f.getResolverStyle() != ResolverStyle.SMART) {
                throw new RuntimeException("Unexpected resolver style for " + style);
            }
            if (f.getChronology() != IsoChronology.INSTANCE) {
                throw new RuntimeException("Unexpected chronology for " + style);
            }
        }
    }
}
