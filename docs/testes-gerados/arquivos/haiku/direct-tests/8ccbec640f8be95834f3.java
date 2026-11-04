/*
 * @test
 * @summary Verifies that DateTimeFormatterBuilder.appendZoneText(TextStyle) returns the same builder and prints zone text.
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testReturnsSameBuilder();
        testFullStylePrintsZoneText();
        testBuilderStillBuildsFormatter();
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendZoneText(TextStyle.FULL);
        if (result != builder) {
            throw new RuntimeException("appendZoneText must return this builder");
        }
    }

    static void testFullStylePrintsZoneText() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDateTime.of(2018, 1, 15, 12, 0),
                ZoneId.of("America/New_York"));
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendZoneText(TextStyle.FULL)
                .toFormatter(Locale.US);
        String s = f.format(zdt);
        if (s == null || s.isEmpty()) {
            throw new RuntimeException("zone text must not be empty");
        }
        if (!s.contains("Eastern")) {
            throw new RuntimeException("unexpected zone text: " + s);
        }
    }

    static void testBuilderStillBuildsFormatter() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendZoneText(TextStyle.SHORT)
                .toFormatter(Locale.US);
        if (f == null) {
            throw new RuntimeException("toFormatter returned null");
        }
    }
}
