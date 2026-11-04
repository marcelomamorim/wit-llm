/*
 * @test
 * @summary Test OffsetDateTime.parse(CharSequence, DateTimeFormatter) method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testParseValid();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String text = "2018-09-01T10:15:30+01:00";
        OffsetDateTime odt = OffsetDateTime.parse(text, formatter);
        if (!odt.toString().equals(text)) {
            throw new AssertionError("Parsed OffsetDateTime does not match input text");
        }
    }

    private static void testParseNullFormatter() {
        try {
            OffsetDateTime.parse("2018-09-01T10:15:30+01:00", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String invalidText = "invalid-date-time";
        try {
            OffsetDateTime.parse(invalidText, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
