/*
 * @test
 * @summary Basic tests for DateTimeFormatterBuilder.appendZoneText(TextStyle)
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.ZoneId;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendZoneTextReturnsBuilder();
        testAppendZoneTextProducesFormatterWithZoneName();
    }

    static void testAppendZoneTextReturnsBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder returned = builder.appendZoneText(TextStyle.FULL);
        if (returned != builder) {
            throw new AssertionError("appendZoneText should return the same builder instance");
        }
    }

    static void testAppendZoneTextProducesFormatterWithZoneName() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendZoneText(TextStyle.SHORT);
        DateTimeFormatter formatter = builder.toFormatter();

        ZonedDateTime zdt = ZonedDateTime.of(2018, 9, 1, 12, 0, 0, 0, ZoneId.of("Europe/Paris"));
        String formatted = formatter.format(zdt);

        // The output should contain a known short zone name like "CEST" or "CET"
        if (formatted == null || formatted.isEmpty()) {
            throw new AssertionError("Formatted zone text should not be empty");
        }
        // Basic check: output should contain at least one uppercase letter (zone name)
        boolean hasUpper = false;
        for (int i = 0; i < formatted.length(); i++) {
            char c = formatted.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                hasUpper = true;
                break;
            }
        }
        if (!hasUpper) {
            throw new AssertionError("Formatted zone text should contain uppercase letters indicating zone name: " + formatted);
        }
    }
}
