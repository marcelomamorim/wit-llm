/*
 * @test
 * @summary Test of DateTimeFormatter.ofLocalizedDateTime(FormatStyle)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.DateTimeFormatterBuilder;
import java.time.chrono.IsoChronology;
import java.time.format.ResolverStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testOfLocalizedDateTimeWithValidStyle();
        testOfLocalizedDateTimeWithNullStyle();
    }

    static void testOfLocalizedDateTimeWithValidStyle() {
        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(style);
        }
    }

    static void testOfLocalizedDateTimeWithNullStyle() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(null);
        } catch (NullPointerException e) {
            // expected
        }
    }
}
