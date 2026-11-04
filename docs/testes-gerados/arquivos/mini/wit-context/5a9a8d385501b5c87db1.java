/*
 * @test
 * @summary Test java.time.ZonedDateTime.parse(CharSequence) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testParseValidString();
        testParseNullTextThrowsNPE();
    }

    private static void testParseValidString() {
        // Use a valid ISO_ZONED_DATE_TIME string
        String text = "2018-09-01T10:15:30+01:00[Europe/Paris]";
        ZonedDateTime zdt = ZonedDateTime.parse(text);
        if (!zdt.toString().equals(text)) {
            throw new AssertionError("Parsed ZonedDateTime string does not match input text");
        }
    }

    private static void testParseNullTextThrowsNPE() {
        try {
            ZonedDateTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
