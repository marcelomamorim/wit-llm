/*
 * @test
 * @summary Basic tests for DateTimeFormatter.parse(CharSequence) method
 * @run main java.time.format.DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testParseNullText();
        testParseValidText();
        testParseInvalidText();
    }

    static void testParseNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testParseValidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String text = "2018-09-01";
        TemporalAccessor result = formatter.parse(text);
        Objects.requireNonNull(result, "parse result should not be null");
    }

    static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String text = "invalid-date";
        try {
            formatter.parse(text);
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
