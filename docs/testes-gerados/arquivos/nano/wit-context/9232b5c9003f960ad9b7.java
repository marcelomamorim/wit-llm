/*
 * @test
 * @summary Tests for java.time.ZonedDateTime.parse(CharSequence, DateTimeFormatter)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
    }

    public static void testParseWithNullFormatter() {
        CharSequence text = "2023-03-25T10:15:30+01:00[Europe/Paris]";
        DateTimeFormatter formatter = null;
        try {
            ZonedDateTime.parse(text, formatter);
            System.err.println("Failed: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testParseWithNullText() {
        CharSequence text = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        try {
            ZonedDateTime.parse(text, formatter);
            System.err.println("Failed: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
