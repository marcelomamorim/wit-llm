/*
 * @test
 * @summary Witup baseline checks for DateTimeFormatterBuilder.appendZoneText(TextStyle)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testReturnsSameBuilderInstance();
        testFormatProducesNonEmptyZoneText();
    }

    static void testReturnsSameBuilderInstance() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendZoneText(TextStyle.SHORT);
        if (result != builder) {
            throw new RuntimeException("appendZoneText must return this builder");
        }
    }

    static void testFormatProducesNonEmptyZoneText() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendZoneText(TextStyle.SHORT)
                .toFormatter(Locale.US);
        ZonedDateTime zdt = ZonedDateTime.of(2018, 9, 1, 12, 0, 0, 0, ZoneId.of("Europe/Paris"));
        String text = formatter.format(zdt);
        if (text == null || text.isEmpty()) {
            throw new RuntimeException("zone text must not be empty");
        }
    }
}
