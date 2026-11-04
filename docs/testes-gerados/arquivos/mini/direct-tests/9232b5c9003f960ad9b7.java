/*
 * @test
 * @summary Test java.time.ZonedDateTime.parse(CharSequence, DateTimeFormatter) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testParseValid();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        String text = "2018-09-01T10:15:30+01:00[Europe/Paris]";
        ZonedDateTime zdt = ZonedDateTime.parse(text, formatter);
        if (!zdt.toString().equals(text)) {
            throw new AssertionError("Parsed ZonedDateTime does not match input text");
        }
    }

    private static void testParseNullFormatter() {
        try {
            ZonedDateTime.parse("2018-09-01T10:15:30+01:00[Europe/Paris]", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        String invalidText = "invalid-date-time";
        try {
            ZonedDateTime.parse(invalidText, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
