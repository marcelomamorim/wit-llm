/*
 * @test
 * @summary Test java.time.ZonedDateTime.parse(CharSequence, DateTimeFormatter) for null arguments and normal parse
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInput();
    }

    private static void testParseWithNullFormatter() {
        try {
            ZonedDateTime.parse("2020-01-01T12:00:00+01:00[Europe/Paris]", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime result = ZonedDateTime.parse(null, formatter);
        if (result != null) {
            throw new AssertionError("Expected parse to return null or throw, but got: " + result);
        }
    }

    private static void testParseWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        String text = "2018-12-03T10:15:30+01:00[Europe/Paris]";
        ZonedDateTime zdt = ZonedDateTime.parse(text, formatter);
        if (zdt == null) {
            throw new AssertionError("Parsed ZonedDateTime should not be null");
        }
        if (!"Europe/Paris".equals(zdt.getZone().getId())) {
            throw new AssertionError("ZoneId mismatch: expected Europe/Paris but got " + zdt.getZone());
        }
        if (zdt.getYear() != 2018 || zdt.getMonthValue() != 12 || zdt.getDayOfMonth() != 3) {
            throw new AssertionError("Date part mismatch: " + zdt);
        }
    }
}
