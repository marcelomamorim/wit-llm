/*
 * @test
 * @summary Test OffsetTime.parse(CharSequence, DateTimeFormatter) with valid and invalid inputs
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;
        String text = "10:15:30+01:00";
        OffsetTime result = OffsetTime.parse(text, formatter);
        if (result == null) {
            throw new AssertionError("parse returned null for valid input");
        }
        if (!"10:15:30+01:00".equals(result.toString())) {
            throw new AssertionError("parse result string mismatch: " + result);
        }
    }

    private static void testParseNullFormatter() {
        try {
            OffsetTime.parse("10:15:30+01:00", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;
        String invalidText = "invalid-time";
        try {
            OffsetTime.parse(invalidText, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
