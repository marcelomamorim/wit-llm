/*
 * @test
 * @summary Test LocalDateTime.parse(CharSequence, DateTimeFormatter) with valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String text = "2018-09-01T12:30:40";
        LocalDateTime ldt = LocalDateTime.parse(text, formatter);
        if (!ldt.toString().equals(text)) {
            throw new AssertionError("Parsed LocalDateTime does not match input text");
        }
    }

    private static void testParseNullFormatter() {
        try {
            LocalDateTime.parse("2018-09-01T12:30:40", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String invalidText = "invalid-date-time";
        try {
            LocalDateTime.parse(invalidText, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
